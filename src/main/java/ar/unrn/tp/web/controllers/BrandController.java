package ar.unrn.tp.web.controllers;

import ar.unrn.tp.api.BrandService;
import ar.unrn.tp.domain.dto.BrandDTO;
import ar.unrn.tp.exceptions.BrandException;
import ar.unrn.tp.web.contracts.BrandContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/marcas")
public class BrandController implements BrandContract {

    @Autowired
    private BrandService brandService;


    @Override
    public ResponseEntity<List<BrandDTO>> listarMarcas() {
        List<BrandDTO> marcas = this.brandService.listarMarcas();

        return ResponseEntity.ok(marcas);
    }
}
