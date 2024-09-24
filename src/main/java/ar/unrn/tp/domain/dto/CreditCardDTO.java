package ar.unrn.tp.domain.dto;

import ar.unrn.tp.domain.models.CreditCard;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class CreditCardDTO {
    @NotNull
    private Long id;
    @NotNull
    private Long number;
    @NotNull
    private CardTypeDTO type;
    @NotNull
    private boolean activate;
    @Positive
    private double funds;

    public static CreditCardDTO fromDomain(CreditCard card){
        return CreditCardDTO.builder()
                .id(card.getId())
                .number(card.getNumber())
                .type(CardTypeDTO.fromDomain(card.getType()))
                .activate(card.isActivate())
                .funds(card.getFunds())
                .build();
    }
}
