package ar.unrn.tp.services;

import ar.unrn.tp.api.InvoiceNumberService;
import ar.unrn.tp.api.TransactionService;
import ar.unrn.tp.domain.models.InvoiceNumber;
import ar.unrn.tp.exceptions.InvoiceNumberException;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class InvoiceNumberServiceImpl implements InvoiceNumberService {

    @Autowired
    private TransactionService transactionService;
    @Override
    public String generateInvoiceNumber() {

        AtomicReference<String> newInvoiceNumber = new AtomicReference<>("");

        this.transactionService.executeInTransaction(em -> {

            int currentYear = LocalDate.now().getYear();

            try {

                String query = "FROM InvoiceNumber WHERE year = :currentYear";
                TypedQuery<InvoiceNumber> result = em.createQuery(query, InvoiceNumber.class);
                result.setParameter("currentYear", currentYear);

                result.setLockMode(LockModeType.PESSIMISTIC_WRITE);

                InvoiceNumber number = result.getSingleResult();

                newInvoiceNumber.set(number.generateInvoiceNumber() + "/" + currentYear);

            } catch (NoResultException ne) {
                InvoiceNumber newNumber = new InvoiceNumber(currentYear, 0);
                em.persist(newNumber);
                newInvoiceNumber.set(newNumber.generateInvoiceNumber() + "/" + currentYear);

            } catch (NonUniqueResultException ue) {
                throw new InvoiceNumberException("Se encontraron múltiples resultados para este año : " + currentYear + ue.getMessage(), ue);

            } catch (Exception e) {
                throw new InvoiceNumberException("Error al generar el numero de factura " + e.getMessage(), e);
            }
        });

        return newInvoiceNumber.get();
    }
}
