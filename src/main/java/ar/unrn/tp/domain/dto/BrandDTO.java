package ar.unrn.tp.domain.dto;

import ar.unrn.tp.domain.models.BrandEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrandDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String name;

    public static BrandDTO fromDomain(BrandEntity brand){
        return BrandDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }
}
