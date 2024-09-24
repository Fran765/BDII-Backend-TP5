package ar.unrn.tp.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
public class BuyDiscount extends Discount {

    @ManyToOne
    @JoinColumn(name = "type_id")
    private CardType cardType;

    public BuyDiscount(float percent, LocalDate startDate, LocalDate endDate, CardType cardType) {
        super(percent, startDate, endDate);
        this.cardType = cardType;
    }

    protected BuyDiscount() {
    }

    public boolean isApply(CreditCard creditCard) {
        return (creditCard.isType(this.cardType)) && (super.isOnDate());
    }

}
