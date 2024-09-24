package ar.unrn.tp.web.contracts;

import ar.unrn.tp.domain.dto.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProductContract {

    @PostMapping("/crear")
    ResponseEntity<Void> crearProducto(@RequestBody @Valid ProductDTO payload);

    @PutMapping("/modificar/{id}")
    ResponseEntity<Void> modificarProducto(@PathVariable Long id, @RequestBody @Valid ProductDTO payload);

    @GetMapping("/")
    ResponseEntity<List<ProductDTO>> listarProductos();
}
