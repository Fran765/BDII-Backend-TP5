package ar.unrn.tp.domain.dto;

import ar.unrn.tp.domain.models.CategoryEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {

    private Long id;
    @NotBlank
    private String name;

    public static CategoryDTO fromDomain(CategoryEntity category){
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
