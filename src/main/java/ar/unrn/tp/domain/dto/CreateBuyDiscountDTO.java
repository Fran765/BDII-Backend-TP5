package ar.unrn.tp.domain.dto;

import ar.unrn.tp.domain.models.BuyDiscount;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;

@Getter
public class CreateBuyDiscountDTO extends DiscountDTO{

    @NonNull
    private final Long idCardType;

    public CreateBuyDiscountDTO(float percent,
                                @NonNull LocalDate startDate,
                                @NonNull LocalDate endDate,
                                @NonNull Long idCardType) {
        super(percent, startDate, endDate);
        this.idCardType = idCardType;
    }

    public static CreateBuyDiscountDTO fromDomain(BuyDiscount buyDiscount){
        return new CreateBuyDiscountDTO(buyDiscount.getPercent(),
                buyDiscount.getStartDate(),
                buyDiscount.getEndDate(),
                buyDiscount.getCardType().getId());
    }
}
