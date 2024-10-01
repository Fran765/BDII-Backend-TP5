package ar.unrn.tp.domain.dto;

import ar.unrn.tp.domain.models.ProductDiscount;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;

@Getter
public class CreateProductDiscountDTO extends DiscountDTO{
    @NotNull
    private final Long idBrand;

    public CreateProductDiscountDTO(float percent,
                                    @NonNull LocalDate startDate,
                                    @NonNull LocalDate endDate,
                                    @NonNull Long idBrand) {
        super(percent, startDate, endDate);
        this.idBrand = idBrand;
    }

    public static CreateProductDiscountDTO fromDomain(ProductDiscount productDiscount){
        return new CreateProductDiscountDTO(productDiscount.getPercent(),
                productDiscount.getStartDate(),
                productDiscount.getEndDate(),
                productDiscount.getBrand().getId());
    }
}
