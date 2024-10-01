package ar.unrn.tp.domain.models;

import ar.unrn.tp.exceptions.ProductException;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Objects;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer code;
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    private double price;

    protected Product() {
    }

    public Product(Integer code, String description, CategoryEntity category, BrandEntity brand, double price) {

        Objects.requireNonNull(category);
        Objects.requireNonNull(brand);

        if (description.isEmpty())
            throw new ProductException("La descripcion no puede estar vacia.");

        if (price <= 0.0)
            throw new ProductException("El precio no puede ser igual a 0.0 o negativo.");

        this.code = code;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.price = price;
    }

    public boolean isBrand(BrandEntity aPotentialBrand) {
        return this.brand.equals(aPotentialBrand);
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateCategory(CategoryEntity category) {
        this.category = category;
    }

    public void updateBrand(BrandEntity brand) {
        this.brand = brand;
    }

    public void updatePrice(double price) {
        this.price = price;
    }
}
