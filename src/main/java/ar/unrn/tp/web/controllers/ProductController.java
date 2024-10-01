package ar.unrn.tp.web.controllers;

import ar.unrn.tp.api.ProductService;
import ar.unrn.tp.domain.dto.ProductCreateDTO;
import ar.unrn.tp.domain.dto.ProductDTO;
import ar.unrn.tp.web.contracts.ProductContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductController implements ProductContract {

    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<Void> crearProducto(ProductCreateDTO payload) {

        this.productService.crearProducto(
                String.valueOf(payload.getCode()),
                payload.getDescription(),
                payload.getPrice(),
                payload.getIdCategory(),
                payload.getIdBrand()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> modificarProducto(Long id, ProductCreateDTO payload) {

        this.productService.modificarProducto(id,
                payload.getDescription(),
                payload.getIdCategory(),
                payload.getIdBrand(),
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
