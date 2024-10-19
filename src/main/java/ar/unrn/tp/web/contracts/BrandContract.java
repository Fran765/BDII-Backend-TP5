package ar.unrn.tp.web.contracts;

import ar.unrn.tp.domain.dto.BrandDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface BrandContract {
    @GetMapping("/")
    ResponseEntity<List<BrandDTO>> listarMarcas();
}
