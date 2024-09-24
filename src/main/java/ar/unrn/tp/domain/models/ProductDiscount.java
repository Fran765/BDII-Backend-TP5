package ar.unrn.tp.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
@Entity
public class ProductDiscount extends Discount {
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    public ProductDiscount(float percent, LocalDate startDate, LocalDate endDate, BrandEntity brand) {
        super(percent, startDate, endDate);
        this.brand = brand;
    }

    protected ProductDiscount() {
        super();
    }

    public boolean isApply(Product product) {
        return (product.isBrand(this.brand)) && (super.isOnDate());
    }
}
