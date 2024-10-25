package ar.unrn.tp.services;

import ar.unrn.tp.api.TransactionService;
import ar.unrn.tp.exceptions.ProductUpdateException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.function.Consumer;

@Service
public class TransactionServiceImpl implements TransactionService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void executeInTransaction(Consumer<EntityManager> action) {
        try {
            action.accept(entityManager);

        } catch (OptimisticLockException e) {
            throw new ProductUpdateException("El producto ha sido actualizado por otro usuario. Por favor, recargue la página e intente nuevamente.");

        } catch (Exception e) {
            throw new RuntimeException("Error durante la transaccion", e);
        }
    }
}