package ar.unrn.tp.domain.models;

import ar.unrn.tp.services.ProductMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Shop {
    private final ProductMapper productMapper;
    private List<ProductDiscount> discountsProduct;
    private List<BuyDiscount> purchaseDiscounts;

    public Shop(ProductMapper productMapper,
                List<ProductDiscount> discountsProduct,
                List<BuyDiscount> purchaseDiscounts) {

        this.productMapper = productMapper;
        this.discountsProduct = discountsProduct;
        this.purchaseDiscounts = purchaseDiscounts;
    }

    public Sale completPurchase(ShoppingCart cart, CreditCard card) {

        double totalPrice = this.calcularTotal(cart, card);

        card.subtractFunds(totalPrice);

        return new Sale(cart.ownerOfCart(), this.parseProducts(cart.getProducts()), totalPrice);
    }


    public double calcularTotal(ShoppingCart cart, CreditCard card) {
        return this.applyDiscountCard(card, cart.totalPriceWithDiscount(this.discountsProduct));
    }

    private double applyDiscountCard(CreditCard creditCard, double totalPrice) {

        return purchaseDiscounts.stream()
                .filter(discount -> discount.isOnDate() && discount.isApply(creditCard))
                .findFirst()
                .map(discount -> discount.applyDiscount(totalPrice))
                .orElse(totalPrice);
    }

    private List<ProductSale> parseProducts(Stream<Product> products) {

        List<ProductSale> soldProducts = new ArrayList<>();

        products.forEach(product ->
                soldProducts.add(productMapper.convertProductToProductSale(product)));

        return soldProducts;
    }
}
