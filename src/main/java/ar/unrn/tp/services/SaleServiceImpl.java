package ar.unrn.tp.services;

import ar.unrn.tp.api.InvoiceNumberService;
import ar.unrn.tp.api.RedisService;
import ar.unrn.tp.api.SaleService;
import ar.unrn.tp.api.TransactionService;
import ar.unrn.tp.domain.dto.SaleDTO;
import ar.unrn.tp.domain.models.*;
import ar.unrn.tp.exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleServiceImpl implements SaleService {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private InvoiceNumberService invoiceNumberService;
    private Shop shop;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {

        List<Product> productList = this.obtenerProductosSeleccionados(productos);
        ShoppingCart cart = crearCarritoConProductos(productList);

        this.inicializarNegocio();

        this.transactionService.executeInTransaction(em -> {
            try {
                CreditCard card = em.find(CreditCard.class, idTarjeta);
                Client client = em.find(Client.class, idCliente);

                if (!client.isMyCard(card))
                    throw new CardException("La tarjeta no corresponde para este cliente.");

                Sale newSale = shop.completPurchase(client, cart, card);

                String newInvoiceNumber = this.invoiceNumberService.generateInvoiceNumber();

                newSale.setInvoiceNumber(newInvoiceNumber);

                em.persist(newSale);

            } catch (NoResultException e){
                throw new ApplicationException("No se econtraron resultados para el id de cliente o la tarjeta: " + e.getMessage());

            } catch (NonUniqueResultException e) {
                throw new ApplicationException("Se encontraron múltiples resultados para este ID de clientes o tarjeta: " + e.getMessage());

            }catch (Exception e) {
                throw new SaleException("Error al querer realizazr la venta: " + e.getMessage());
            }
        });

        String cacheKey = "ultimas/ventas:" + idCliente;
        this.redisService.executeInRedis(jedis -> jedis.del(cacheKey));
    }

    @Override
    public double calcularMonto(List<Long> productos, Long idTarjeta) {
        double montoTotal = 0;

        CreditCard card = getCreditCard(idTarjeta);

        if (card == null)
            throw new CardException("No se encontró la tarjeta con ID: " + idTarjeta);

        this.inicializarNegocio();

        List<Product> productList = this.obtenerProductosSeleccionados(productos);
        ShoppingCart cart = crearCarritoConProductos(productList);

        montoTotal = shop.calcularTotal(cart, card);

        return montoTotal;
    }

    @Override
    public List<SaleDTO> ventas() {

        List<Sale> ventas = new ArrayList<>();

        this.transactionService.executeInTransaction(em -> {
            try {
                TypedQuery<Sale> sql = em.createQuery("SELECT s FROM Sale s", Sale.class);
                ventas.addAll(sql.getResultList());

            } catch (PersistenceException e){
                throw new SaleException("Error al recuperar las ventas: " + e.getMessage());
            }
        });

        return ventas.stream()
                .map(SaleDTO::fromDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaleDTO> ventasRecientes(Long idCliente){
        String key = "ultimas/ventas:" + idCliente;

        List<SaleDTO> ventas = new ArrayList<>();

        this.redisService.executeInRedis( jedis -> {
            try{
                if (jedis.exists(key)) {
                    String saleJson = jedis.get(key);
                    SaleDTO[] sales = objectMapper.readValue(saleJson, SaleDTO[].class);
                    ventas().addAll(Arrays.asList(sales));
                } else {

                    List<Sale> sales = listarVentasPorCliente(idCliente);
                    if(sales.isEmpty())
                        return;

                    String saleJson = objectMapper.writeValueAsString(sales);
                    jedis.set(key, saleJson);

                    jedis.expire(key, 120);

                    ventas().addAll(sales.stream()
                                    .map(SaleDTO::fromDomain)
                                    .toList());
                }
            }catch (JsonProcessingException e){
                throw new SaleException("Error al procesar JSON con Jackson");
            }
        });

        return ventas;
    }

    private List<Sale> listarVentasPorCliente(Long idCliente) {
        try{
            List<Sale> ventas = new ArrayList<>();

            this.transactionService.executeInTransaction(em -> {
                try {
                    TypedQuery<Sale> query = em.createQuery("SELECT s FROM Sale s WHERE s.client.id = :idCliente ORDER BY s.dateAndTime DESC", Sale.class);
                    query.setParameter("idCliente", idCliente);
                    query.setMaxResults(3);

                    ventas.addAll(query.getResultList());

                } catch (PersistenceException e) {
                    throw new SaleException("Error al recuperar las ventas: " + e.getMessage());
                }
            });

            return ventas;
        } catch(Exception e){
            throw new SaleException(e.getMessage());
        }
    }

    private CreditCard getCreditCard(Long idTarjeta) {
        AtomicReference<CreditCard> cardReference = new AtomicReference<>();

        this.transactionService.executeInTransaction(em -> {

            try {
                CreditCard card = em.find(CreditCard.class, idTarjeta);
                cardReference.set(card);

            } catch (NoResultException e) {
                throw new CardException("No se econtro una tarjeta para el ID: " + idTarjeta + e.getMessage());

            } catch (NonUniqueResultException e) {
                throw new CardException("Se encontraron múltiples resultados para este ID de clientes o tarjeta: " + e.getMessage());

            } catch (PersistenceException e) {
                throw new SaleException("Error al querer calcular el monto de la venta: " + e.getMessage());
            }
        });

        return cardReference.get();
    }

    private void inicializarNegocio(){
        List<BuyDiscount> cardDiscount = new ArrayList<>();
        List<ProductDiscount> productDiscounts = new ArrayList<>();

        this.transactionService.executeInTransaction( em -> {
            try {
                cardDiscount.addAll(em.createQuery("SELECT bd FROM BuyDiscount bd", BuyDiscount.class).getResultList());

                productDiscounts.addAll(em.createQuery("SELECT pd FROM ProductDiscount pd", ProductDiscount.class).getResultList());

            } catch (PersistenceException e){
                throw new DiscountException("Error al recuperar los descuentos: " + e.getMessage());
            }
        });

        shop = new Shop(Mappers.getMapper(ProductMapper.class), productDiscounts, cardDiscount);
    }

    private List<Product> obtenerProductosSeleccionados(List<Long> idProducts) {

        if (idProducts.isEmpty())
            throw new ProductException("La lista de refencia de productos esta vacia.");

        List<Product> productsList = new ArrayList<>();

        this.transactionService.executeInTransaction(em -> {

                for (Long idProduct : idProducts) {
                    try {
                        Product product = em.find(Product.class, idProduct);

                        if (product == null)
                            throw new ProductException("Producto no encontrado: " + idProduct);

                        productsList.add(product);

                    } catch (NoResultException e) {
                        throw new ProductException("No se econtro una tarjeta para el ID: " + idProduct + e.getMessage());

                    } catch (NonUniqueResultException e) {
                        throw new ProductException("Se encontraron múltiples resultados para este ID de clientes o tarjeta: " + e.getMessage());

                    } catch (PersistenceException e){
                        throw new ProductException("Error al buscar producto: " + e.getMessage());
                    }
                }
        });

        return productsList;
    }

    private ShoppingCart crearCarritoConProductos(List<Product> productList) {

        ShoppingCart cart = new ShoppingCart();
        
        for (Product product : productList) {
            cart.addProduct(product);
        }
        return cart;
    }

}
