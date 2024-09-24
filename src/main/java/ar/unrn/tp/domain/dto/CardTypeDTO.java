package ar.unrn.tp.domain.dto;

import ar.unrn.tp.domain.models.CardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardTypeDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String name;

    public static CardTypeDTO fromDomain(CardType typeCard){
        return CardTypeDTO.builder()
                .id(typeCard.getId())
                .name(typeCard.getName())
                .build();
    }
}
