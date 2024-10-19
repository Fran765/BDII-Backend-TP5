package ar.unrn.tp.web.contracts;

import ar.unrn.tp.domain.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

public interface CategoryContract {

    @GetMapping("/")
    ResponseEntity<List<CategoryDTO>> listarTarjetas();
}
