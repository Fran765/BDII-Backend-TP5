package ar.unrn.tp.domain.dto;

import ar.unrn.tp.domain.models.BrandEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrandDTO {

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
