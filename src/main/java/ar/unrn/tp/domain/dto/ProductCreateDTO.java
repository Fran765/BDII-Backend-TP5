package ar.unrn.tp.domain.dto;

import ar.unrn.tp.domain.models.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCreateDTO {

    private Long id;
    @NotNull
    private Integer code;
    @NotBlank
    private String description;
    @NotNull
    private Long idCategory;
    @NotNull
    private Long idBrand;
    @Positive
    private double price;

    public static ProductCreateDTO fromDomain(Product product){
        return ProductCreateDTO.builder()
                .id(product.getId())
                .code(product.getCode())
                .description(product.getDescription())
                .idCategory(product.getCategory().getId())
                .idBrand(product.getBrand().getId())
                .price(product.getPrice())
                .build();
    }
}
