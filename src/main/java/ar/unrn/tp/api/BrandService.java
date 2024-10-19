package ar.unrn.tp.api;

import ar.unrn.tp.domain.dto.BrandDTO;

import java.util.List;

public interface BrandService {

    List<BrandDTO> listarMarcas();
}
