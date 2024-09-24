package ar.unrn.tp.web.controllers;

import ar.unrn.tp.api.ProductService;
import ar.unrn.tp.domain.dto.ProductDTO;
import ar.unrn.tp.web.contracts.ProductContract;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductController implements ProductContract {

    private final ProductService productService;

    @Override
    public ResponseEntity<Void> crearProducto(ProductDTO payload) {

        this.productService.crearProducto(
                String.valueOf(payload.getCode()),
                payload.getDescription(),
                payload.getPrice(),
                payload.getCategory().getId(),
                payload.getBrand().getId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> modificarProducto(Long id, ProductDTO payload) {

        this.productService.modificarProducto(id,
                payload.getDescription(),
                payload.getCategory().getId(),
                payload.getBrand().getId(),
                payload.getPrice()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<List<ProductDTO>> listarProductos() {

        List<ProductDTO> productos = this.productService.listarProductos();

        return ResponseEntity.ok(productos);
    }
}
