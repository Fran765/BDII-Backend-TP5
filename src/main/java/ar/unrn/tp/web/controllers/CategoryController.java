package ar.unrn.tp.web.controllers;

import ar.unrn.tp.api.CategoryService;
import ar.unrn.tp.domain.dto.CategoryDTO;
import ar.unrn.tp.exceptions.ApplicationException;
import ar.unrn.tp.exceptions.CategoryException;
import ar.unrn.tp.web.contracts.CategoryContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoryController implements CategoryContract {

    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseEntity<List<CategoryDTO>> listarTarjetas() {

        List<CategoryDTO> categorias = this.categoryService.listarCategorias();

        return ResponseEntity.ok(categorias);
    }
}
