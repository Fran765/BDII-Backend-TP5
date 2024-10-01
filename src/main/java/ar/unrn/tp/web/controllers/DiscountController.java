package ar.unrn.tp.web.controllers;

import ar.unrn.tp.api.DiscountService;
import ar.unrn.tp.domain.dto.*;
import ar.unrn.tp.exceptions.ApplicationException;
import ar.unrn.tp.exceptions.DiscountException;
import ar.unrn.tp.web.contracts.DiscountContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/descuentos")
public class DiscountController implements DiscountContract {

    @Autowired
    private DiscountService discountService;

    @Override
    public ResponseEntity<Void> crearDescuentoSobreTotal(CreateBuyDiscountDTO payload) {
        try {
            this.discountService.crearDescuentoSobreTotal(payload.getIdCardType(),
                    payload.getStartDate(),
                    payload.getEndDate(),
                    payload.getPercent()
            );

            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch(DiscountException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error

        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        }
    }

    @Override
    public ResponseEntity<Void> crearDescuento(CreateProductDiscountDTO payload) {
        try {
            this.discountService.crearDescuento(payload.getIdBrand(),
                    payload.getStartDate(),
                    payload.getEndDate(),
                    payload.getPercent()
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch(DiscountException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error

        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        }
    }

    @Override
    public ResponseEntity<List<DiscountDTO>> listarDescuentosActivos() {
        try {
            List<DiscountDTO> descuentos = this.discountService.listarDescuentosActivos();

            return ResponseEntity.ok(descuentos);

        } catch(DiscountException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error

        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        }
    }
}
