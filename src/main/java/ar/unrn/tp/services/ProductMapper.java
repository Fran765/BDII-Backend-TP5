package ar.unrn.tp.services;

import ar.unrn.tp.domain.models.BrandEntity;
import ar.unrn.tp.domain.models.CategoryEntity;
import ar.unrn.tp.domain.models.Product;
import ar.unrn.tp.domain.models.ProductSale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "brand.name", target = "brand")
    ProductSale convertProductToProductSale(Product product);

    default String map(CategoryEntity category) {
        return category != null ? category.getName() : null;
    }

    default String map(BrandEntity brand) {
        return brand != null ? brand.getName() : null;
    }
}
