package ar.unrn.tp.services;

import ar.unrn.tp.api.ProductService;
import ar.unrn.tp.api.TransactionService;
import ar.unrn.tp.domain.dto.ProductDTO;
import ar.unrn.tp.domain.models.Product;
import ar.unrn.tp.domain.models.BrandEntity;
import ar.unrn.tp.domain.models.CategoryEntity;
import ar.unrn.tp.exceptions.ProductException;
import ar.unrn.tp.exceptions.ProductUpdateException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private TransactionService transactionService;

    @Override
    public void crearProducto(String codigo, String descripcion, double precio, Long idCategoria, Long idMarca) {
        this.transactionService.executeInTransaction(em -> {

            validateCode(em, codigo);

            try {
                Product newProduct = new Product(Integer.parseInt(codigo),
                        descripcion,
                        getObjectDiscountById(em, idCategoria, false, CategoryEntity.class),
                        getObjectDiscountById(em, idMarca, true, BrandEntity.class),
                        precio);

                em.persist(newProduct);

            } catch (NumberFormatException e) {
                throw new ProductException("El formato del codigo para crear el producto es incorrecto." + e.getMessage());

            } catch (Exception e) {
                throw new ProductException("Error al crear el producto: " + e.getMessage());
            }
        });
    }

    @Override
    public ProductDTO modificarProducto(Long idProducto, String descripcion, Long idCategoria, Long idMarca, double price, Long version) {

        AtomicReference<Product> productUpdated = new AtomicReference<>();

        this.transactionService.executeInTransaction(em -> {
            String queryGetProducts = "SELECT p FROM Product p WHERE p.id = :idProduct";
            TypedQuery<Product> query = em.createQuery(queryGetProducts, Product.class);
            query.setParameter("idProduct", idProducto);

            BrandEntity brand = getObjectDiscountById(em, idMarca, true, BrandEntity.class);
            CategoryEntity category = getObjectDiscountById(em, idCategoria, false, CategoryEntity.class);

            try {
                productUpdated.set(query.getSingleResult());

                if(!Objects.equals(productUpdated.get().getVersion(), version))
                    throw new OptimisticLockException();

                productUpdated.get().updateDescription(descripcion);
                productUpdated.get().updateCategory(category);
                productUpdated.get().updateBrand(brand);
                productUpdated.get().updatePrice(price);

            } catch (NoResultException e) {
                throw new ProductException("No se encontró ningún producto en la base de datos.");

            } catch (Exception e) {
                throw new ProductException("Error al actualizar el producto: " + e.getMessage());
            }
        });

        return ProductDTO.fromDomain(productUpdated.get());
    }

    @Override
    public List<ProductDTO> listarProductos() {

        List<Product> products = new ArrayList<>();

        try {

            this.transactionService.executeInTransaction(em -> {
                String queryGetProducts = "SELECT p FROM Product p";
                TypedQuery<Product> query = em.createQuery(queryGetProducts, Product.class);

                products.addAll(query.getResultList());

            });

            if (products.isEmpty())
                throw new ProductException("No hay productos cargados");

            return products.stream()
                    .map(ProductDTO::fromDomain)
                    .collect(Collectors.toList());

        } catch (Exception e){
            throw new ProductException("Eror al querer recuperar la lista de productos." + e.getMessage());
        }
    }

    private void validateCode(EntityManager em, String code) {

        String sql = "SELECT p FROM Product p WHERE p.code = :productCode";
        TypedQuery<Product> query = em.createQuery(sql, Product.class);
        query.setParameter("productCode", code);

        List<Product> productos = query.getResultList();

        if (!productos.isEmpty()) {
            throw new ProductException("Ya existe un producto con el código: " + code);
        }
    }

    private <T> T getObjectDiscountById(EntityManager em, Long id, boolean isBrand, Class<T> type) {
        String sql = "SELECT ct FROM CategoryEntity ct WHERE ct.id = :idType";

        if (isBrand)
            sql = "SELECT b FROM BrandEntity b WHERE b.id = :idType";

        TypedQuery<T> query = em.createQuery(sql, type);
        query.setParameter("idType", id);

        try {
            return query.getSingleResult();

        } catch (NoResultException e) {
            String entityName = isBrand ? "Tipo de tarjeta" : "Marca";
            throw new ProductException(entityName + " con ID " + id + " no encontrado.");

        } catch (NonUniqueResultException e) {
            String entityName = isBrand ? "un tipo de tarjeta" : " una marca";
            throw new ProductException("existe mas de" + entityName + " con ID " + id );

        } catch (Exception e) {
            String entityName = isBrand ? "tarjeta" : "marca";
            throw new ProductException("Error al querer recuperar la" + entityName );
        }
    }

}