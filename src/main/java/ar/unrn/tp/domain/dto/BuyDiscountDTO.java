package ar.unrn.tp.domain.dto;

import ar.unrn.tp.domain.models.BuyDiscount;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import java.time.LocalDate;

@Getter
public class BuyDiscountDTO extends DiscountDTO{

    @NonNull
    private CardTypeDTO cardType;

    public BuyDiscountDTO(float percent, @NonNull LocalDate startDate, @NonNull LocalDate endDate, @NonNull CardTypeDTO cardType) {
        super(percent, startDate, endDate);
        this.cardType = cardType;
    }

    public static BuyDiscountDTO fromDomain(BuyDiscount buyDiscount){
        return new BuyDiscountDTO(buyDiscount.getPercent(),
                buyDiscount.getStartDate(),
                buyDiscount.getEndDate(),
                CardTypeDTO.fromDomain(buyDiscount.getCardType()));
    }
}
