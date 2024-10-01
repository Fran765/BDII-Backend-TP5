package ar.unrn.tp.domain.dto;

import ar.unrn.tp.domain.models.CreditCard;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateCreditCardDTO {
    private Long id;
    @NotNull
    private Long number;
    @NotNull
    private Long idCardType;
    @NotNull
    private boolean activate;
    @Positive
    private double funds;

    public static CreateCreditCardDTO fromDomain(CreditCard card){
        return CreateCreditCardDTO.builder()
                .id(card.getId())
                .number(card.getNumber())
                .idCardType(card.getType().getId())
                .activate(card.isActivate())
                .funds(card.getFunds())
                .build();
    }
}
