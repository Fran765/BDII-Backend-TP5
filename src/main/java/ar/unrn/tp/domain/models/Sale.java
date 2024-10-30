package ar.unrn.tp.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dateAndTime;
    @ManyToOne
    private Client client;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductSale> products;
    private double totalPrice;
    @ManyToOne
    private CreditCard card;

    private String invoiceNumber;

    public Sale(Client client, List<ProductSale> products, double totalPrice, CreditCard card) {

        this.dateAndTime = LocalDateTime.now();
        this.client = client;
        this.totalPrice = totalPrice;
        this.products = products;
        this.card = card;
    }

    protected Sale() {
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
}