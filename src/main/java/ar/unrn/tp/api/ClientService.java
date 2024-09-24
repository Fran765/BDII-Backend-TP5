package ar.unrn.tp.api;

import ar.unrn.tp.domain.dto.CreditCardDTO;
import java.util.List;

public interface ClientService {
    // validar que el dni no se repita
    void crearCliente(String nombre, String apellido, Integer dni, String email);

    // validar que sea un cliente existente
    void modificarCliente(Long idCliente, String nombre, String apellido, String email);

    // validar que sea un cliente existente
    void agregarTarjeta(Long idCliente, String nro, Long idMarca);

    //Devuelve las tarjetas de un cliente específico
    List<CreditCardDTO> listarTarjetas(Long idCliente);
}
