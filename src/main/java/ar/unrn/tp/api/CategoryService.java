package ar.unrn.tp.api;

import ar.unrn.tp.domain.dto.CategoryDTO;
import java.util.List;

public interface CategoryService {
    List<CategoryDTO> listarCategorias();
}
