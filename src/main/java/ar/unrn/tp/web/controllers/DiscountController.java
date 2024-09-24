package ar.unrn.tp.web.controllers;

import ar.unrn.tp.api.DiscountService;
import ar.unrn.tp.domain.dto.BuyDiscountDTO;
import ar.unrn.tp.domain.dto.ProductDiscountDTO;
import ar.unrn.tp.web.contracts.DiscountContract;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/descuentos")
@RequiredArgsConstructor
public class DiscountController implements DiscountContract {

    private final DiscountService discountService;

    @Override
    public ResponseEntity<Void> crearDescuentoSobreTotal(BuyDiscountDTO payload) {

        this.discountService.crearDescuentoSobreTotal(payload.getCardType().getId(),
                payload.getStartDate(),
                payload.getEndDate(),
                payload.getPercent()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> crearDescuento(ProductDiscountDTO payload) {

        this.discountService.crearDescuento(payload.getCardType().getId(),
                payload.getStartDate(),
                payload.getEndDate(),
                payload.getPercent()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
