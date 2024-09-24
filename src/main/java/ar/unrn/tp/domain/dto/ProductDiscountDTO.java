package ar.unrn.tp.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDiscountDTO extends DiscountDTO{
    @NotNull
    private CardTypeDTO cardType;

}
