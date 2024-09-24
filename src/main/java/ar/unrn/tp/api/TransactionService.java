package ar.unrn.tp.api;

import jakarta.persistence.EntityManager;
import java.util.function.Consumer;

public interface TransactionService {
    void executeInTransaction(Consumer<EntityManager> action);
}