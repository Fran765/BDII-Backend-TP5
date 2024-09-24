package ar.unrn.tp.web.contracts;

import ar.unrn.tp.domain.dto.BuyDiscountDTO;
import ar.unrn.tp.domain.dto.ProductDiscountDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface DiscountContract {

    @PostMapping("/crearDescuentoTotal")
    ResponseEntity<Void> crearDescuentoSobreTotal(@RequestBody @Valid BuyDiscountDTO payload);

    @PostMapping("/crearDescuentoProducto")
    ResponseEntity<Void> crearDescuento(@RequestBody @Valid ProductDiscountDTO payload);
}
