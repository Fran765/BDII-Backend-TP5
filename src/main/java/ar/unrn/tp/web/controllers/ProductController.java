package ar.unrn.tp.web.controllers;

import ar.unrn.tp.api.ProductService;
import ar.unrn.tp.domain.dto.ProductCreateDTO;
import ar.unrn.tp.domain.dto.ProductDTO;
import ar.unrn.tp.exceptions.ApplicationException;
import ar.unrn.tp.exceptions.ProductException;
import ar.unrn.tp.exceptions.ProductUpdateException;
import ar.unrn.tp.exceptions.SaleException;
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

        try{

            this.productService.crearProducto(
                    String.valueOf(payload.getCode()),
                    payload.getDescription(),
                    payload.getPrice(),
                    payload.getIdCategory(),
                    payload.getIdBrand()
            );

            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    public ResponseEntity<?> modificarProducto(Long id, ProductCreateDTO payload) {
        try {
            ProductDTO product = this.productService.modificarProducto(id,
                    payload.getDescription(),
                    payload.getIdCategory(),
                    payload.getIdBrand(),
                    payload.getPrice()
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(product);

        } catch (ProductUpdateException p) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(p.getMessage());

        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<ProductDTO>> listarProductos() {
        try {
            List<ProductDTO> productos = this.productService.listarProductos();

            return ResponseEntity.ok(productos);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
