package ar.unrn.tp.services;

import ar.unrn.tp.api.DiscountService;
import ar.unrn.tp.api.TransactionService;
import ar.unrn.tp.domain.dto.BuyDiscountDTO;
import ar.unrn.tp.domain.dto.DiscountDTO;
import ar.unrn.tp.domain.dto.ProductDiscountDTO;
import ar.unrn.tp.domain.models.*;
import ar.unrn.tp.exceptions.DiscountException;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private TransactionService transactionService;

    @Override
    public void crearDescuentoSobreTotal(Long idMarcaTarjeta, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {

        this.validarDescuentosActivos();

        this.transactionService.executeInTransaction(em -> {
            CardType cardType = getObjectDiscountById(em, idMarcaTarjeta, false, CardType.class);
            BuyDiscount totalDiscount = new BuyDiscount(porcentaje,
                    fechaDesde,
                    fechaHasta,
                    cardType);
            em.persist(totalDiscount);
        });
    }

    @Override
    public void crearDescuento(Long idMarcaProducto, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
        this.transactionService.executeInTransaction(em -> {
            BrandEntity brand = getObjectDiscountById(em, idMarcaProducto, true, BrandEntity.class);
                    ProductDiscount productDiscount = new ProductDiscount(porcentaje,
                        fechaDesde,
                        fechaHasta,
                        brand);
            em.persist(productDiscount);
        });
    }
    @Override
    public List<DiscountDTO> listarDescuentosActivos(){
        List<DiscountDTO> activeDicounts = new ArrayList<>();
        LocalDate today = LocalDate.now();

        List<ProductDiscount> productDiscounts = getProductDiscounts();
        List<BuyDiscount> buyDiscounts = getBuyDiscounts();

        activeDicounts.addAll(productDiscounts.stream()
                .filter(pd -> !pd.getEndDate().isBefore(today))
                .map(ProductDiscountDTO::fromDomain)
                .toList()
        );

        activeDicounts.addAll(buyDiscounts.stream()
                .filter(bd -> !bd.getEndDate().isBefore(today))
                .map(BuyDiscountDTO::fromDomain)
                .toList()
        );

        return activeDicounts;
    }

    private void validarDescuentosActivos(){
        LocalDate today = LocalDate.now();
        List<BuyDiscount> buyDiscounts = this.getBuyDiscounts().stream()
                .filter(bd->!bd.getEndDate().isBefore(today))
                .toList();
        if (!buyDiscounts.isEmpty())
            throw new DiscountException("Existe un descuento de compra activo.");
    }

    private List<ProductDiscount> getProductDiscounts(){
        List<ProductDiscount> productDiscounts = new ArrayList<>();

        this.transactionService.executeInTransaction( em -> {
            try {

                productDiscounts.addAll(em.createQuery("SELECT pd FROM ProductDiscount pd", ProductDiscount.class).getResultList());

            } catch (PersistenceException e){
                throw new DiscountException("Error al recuperar los descuentos de producto: " + e.getMessage());
            }
        });
        return productDiscounts;
    }
    private List<BuyDiscount> getBuyDiscounts(){
        List<BuyDiscount> cardDiscount = new ArrayList<>();

        this.transactionService.executeInTransaction( em -> {
            try {
                cardDiscount.addAll(em.createQuery("SELECT bd FROM BuyDiscount bd", BuyDiscount.class).getResultList());

            } catch (PersistenceException e){
                throw new DiscountException("Error al recuperar los descuentos de compra: " + e.getMessage());
            }
        });
        return cardDiscount;
    }



    private <T> T getObjectDiscountById(EntityManager em, Long id, boolean isBrand, Class<T> type) {
        String sql = "SELECT ct FROM CardType ct WHERE ct.id = :idType";

        if (isBrand)
            sql = "SELECT b FROM BrandEntity b WHERE b.id = :idType";

        TypedQuery<T> query = em.createQuery(sql, type);
        query.setParameter("idType", id);

        try {
            return query.getSingleResult();

        } catch (NoResultException e) {
            throw new DiscountException("Marca con ID " + id + " no encontrado. " + e.getMessage());

        } catch (NonUniqueResultException e) {
            throw new DiscountException("Se encontraron múltiples marcas para este id: " + id + e.getMessage());
        }
    }
}
