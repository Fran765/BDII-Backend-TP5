package ar.unrn.tp.services;

import ar.unrn.tp.api.TransactionService;
import java.util.function.Consumer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {
    // TODO: esto era para utilizar object-db
    /*private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-objectdb");

    @Override
    public void executeInTransaction(Consumer<EntityManager> action) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            action.accept(em);

            tx.commit();

        } catch (Exception e) {

            tx.rollback();
            throw e;

        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }*/


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