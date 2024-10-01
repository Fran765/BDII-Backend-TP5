package ar.unrn.tp.services;

import ar.unrn.tp.api.TransactionService;
import jakarta.persistence.EntityManager;
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
        } catch (Exception e) {
            throw new RuntimeException("Error durante la transaccion", e);
        }
    }
}