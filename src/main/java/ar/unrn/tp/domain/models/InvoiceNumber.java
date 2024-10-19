package ar.unrn.tp.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class InvoiceNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int year;
    private int currentNumber;

    public InvoiceNumber(int year, int currentNumber) {
        this.year = year;
        this.currentNumber = currentNumber;
    }

    protected InvoiceNumber() {
    }

    public int generateInvoiceNumber() {
        this.currentNumber += 1;
        return this.currentNumber;
    }
}
