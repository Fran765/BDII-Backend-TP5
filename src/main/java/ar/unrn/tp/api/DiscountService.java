package ar.unrn.tp.api;

import ar.unrn.tp.domain.dto.DiscountDTO;

import java.time.LocalDate;
import java.util.List;

public interface DiscountService {
    // validar que las fechas no se superpongan
    void crearDescuentoSobreTotal(Long idMarcaTarjeta, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje);

    // validar que las fechas no se superpongan
    void crearDescuento(Long idMarcaProducto, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje);

    List<DiscountDTO> listarDescuentosActivos();

}
