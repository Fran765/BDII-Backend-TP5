package ar.unrn.tp.web.contracts;

import ar.unrn.tp.domain.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DiscountContract {

    @PostMapping("/crearDescuentoTotal")
    ResponseEntity<Void> crearDescuentoSobreTotal(@RequestBody CreateBuyDiscountDTO payload);

    @PostMapping("/crearDescuentoProducto")
    ResponseEntity<Void> crearDescuento(@RequestBody CreateProductDiscountDTO payload);

    @GetMapping("/")
    ResponseEntity<List<DiscountDTO>> listarDescuentosActivos();
}
