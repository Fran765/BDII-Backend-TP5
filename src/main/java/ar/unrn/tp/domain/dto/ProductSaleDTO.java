package ar.unrn.tp.domain.dto;

import ar.unrn.tp.domain.models.ProductSale;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductSaleDTO {
    @NotNull
    private Integer code;
    @NotBlank
    private String description;
    @NotBlank
    private String category;
    @NotBlank
    private String brand;
    @Positive
    private double price;

    public static ProductSaleDTO fromDomain(ProductSale productSale){
        return ProductSaleDTO.builder()
                .code(productSale.getCode())
                .description(productSale.getDescription())
                .category(productSale.getCategory())
                .brand(productSale.getBrand())
                .price(productSale.getPrice())
                .build();
    }

}
