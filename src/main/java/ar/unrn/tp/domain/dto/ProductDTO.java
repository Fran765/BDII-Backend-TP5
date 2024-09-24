package ar.unrn.tp.domain.dto;

import ar.unrn.tp.domain.models.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    @NotNull
    private Long id;
    @NotNull
    private Integer code;
    @NotBlank
    private String description;
    @NotNull
    private CategoryDTO category;
    @NotNull
    private BrandDTO brand;
    @Positive
    private double price;

    public static ProductDTO fromDomain(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .code(product.getCode())
                .description(product.getDescription())
                .category(CategoryDTO.fromDomain(product.getCategory()))
                .brand(BrandDTO.fromDomain(product.getBrand()))
                .price(product.getPrice())
                .build();
    }
}
