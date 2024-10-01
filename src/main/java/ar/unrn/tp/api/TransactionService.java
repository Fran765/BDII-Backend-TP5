package ar.unrn.tp.api;

import java.util.function.Consumer;
import jakarta.persistence.EntityManager;

public interface TransactionService {
    void executeInTransaction(Consumer<EntityManager> action);
}