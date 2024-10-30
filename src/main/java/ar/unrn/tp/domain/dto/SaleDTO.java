package ar.unrn.tp.domain.dto;

import ar.unrn.tp.domain.models.ProductSale;
import ar.unrn.tp.domain.models.Sale;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
//@Jacksonized
public class SaleDTO {
    @NotNull
    private LocalDateTime dateAndTime;
    @NotNull
    private ClientDTO client;
    @NotNull
    private List<ProductSaleDTO> products;
    @Positive
    private double totalPrice;
    @NotNull
    private CreditCardDTO card;
    @NotNull
    private String invoiceNumber;

    //public SaleDTO(){}
    public static SaleDTO fromDomain(Sale sale){

        return SaleDTO.builder()
                .dateAndTime(sale.getDateAndTime())
                .client(ClientDTO.fromDomain(sale.getClient()))
                .products(parseList(sale.getProducts()))
                .totalPrice(sale.getTotalPrice())
                .card(CreditCardDTO.fromDomain((sale.getCard())))
                .invoiceNumber(sale.getInvoiceNumber())
                .build();
    }

    private static List<ProductSaleDTO> parseList(List<ProductSale> products){
        return products.stream()
                .map(ProductSaleDTO :: fromDomain)
                .collect(Collectors.toList());
    }
}
