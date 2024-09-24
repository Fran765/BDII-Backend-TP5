package ar.unrn.tp.domain.models;

import ar.unrn.tp.exceptions.DiscountException;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    protected float percent;
    protected LocalDate startDate;
    protected LocalDate endDate;

    public Discount(float percent, LocalDate startDate, LocalDate endDate) {
        this.percent = percent;
        this.validateDates(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    protected Discount() {
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.equals(endDate))
            throw new DiscountException("Las fechas del descuento no pueden ser iguales.");

        if (startDate.isAfter(endDate))
            throw new DiscountException("La fecha de inicio no puede ser mayor que la ficha de fin.");

    }

    public double applyDiscount(double price) {
        return price * (1 - this.percent / 100);
    }

    protected boolean isOnDate() {
        LocalDate today = LocalDate.now();
        return !today.isBefore(startDate) && !today.isAfter(endDate);
    }
}
