package ar.unrn.tp.web.contracts;

import ar.unrn.tp.domain.dto.ProductRequestIds;
import ar.unrn.tp.domain.dto.SaleDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface SaleContract {

    @PostMapping("/finalizar-compra/{idCliente}/{idTarjeta}")
    ResponseEntity<Void> realizarVenta(@PathVariable Long idCliente, @PathVariable Long idTarjeta, @RequestBody @Valid ProductRequestIds productos);

    @GetMapping("/monto-total/{idTarjeta}")
    ResponseEntity<Double> calcularMonto(@PathVariable Long idTarjeta, @RequestBody @Valid ProductRequestIds productos);

    @GetMapping("/")
    ResponseEntity<List<SaleDTO>> ventas();
}
