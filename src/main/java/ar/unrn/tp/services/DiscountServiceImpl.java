package ar.unrn.tp.services;

import ar.unrn.tp.api.DiscountService;
import ar.unrn.tp.api.TransactionService;
import ar.unrn.tp.domain.models.BrandEntity;
import ar.unrn.tp.domain.models.BuyDiscount;
import ar.unrn.tp.domain.models.CardType;
import ar.unrn.tp.domain.models.ProductDiscount;
import ar.unrn.tp.exceptions.DiscountException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final TransactionService transactionService;

    @Override
    public void crearDescuentoSobreTotal(Long idMarcaTarjeta, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
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
