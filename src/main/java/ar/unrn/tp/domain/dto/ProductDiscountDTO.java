package ar.unrn.tp.domain.dto;

import ar.unrn.tp.domain.models.ProductDiscount;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import java.time.LocalDate;

@Getter
public class ProductDiscountDTO extends DiscountDTO{
    @NotNull
    private BrandDTO brandDTO;

    public ProductDiscountDTO(float percent, @NonNull LocalDate startDate, @NonNull LocalDate endDate, @NonNull BrandDTO brandDTO) {
        super(percent, startDate, endDate);
        this.brandDTO = brandDTO;
    }

    public static ProductDiscountDTO fromDomain(ProductDiscount productDiscount){
        return new ProductDiscountDTO(productDiscount.getPercent(),
                productDiscount.getStartDate(),
                productDiscount.getEndDate(),
                BrandDTO.fromDomain(productDiscount.getBrand()));
    }
}
