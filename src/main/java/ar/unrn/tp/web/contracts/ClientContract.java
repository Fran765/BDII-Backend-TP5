package ar.unrn.tp.web.contracts;

import ar.unrn.tp.domain.dto.ClientDTO;
import ar.unrn.tp.domain.dto.CreditCardDTO;
import ar.unrn.tp.domain.dto.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ClientContract {

    @PostMapping("/crear")
    ResponseEntity<Void> crearCliente(@RequestBody @Valid ClientDTO payload);

    @PutMapping("/modificar/{id}")
    ResponseEntity<Void> modificarCliente(@PathVariable Long id, @RequestBody @Valid ClientDTO payload);

    @PostMapping("/agregarTarjeta/{id}")
    ResponseEntity<Void> agregarTarjeta(@PathVariable Long id, @RequestBody @Valid CreditCardDTO payload);

    @GetMapping("/listarTarjetas/{id}")
    ResponseEntity<List<CreditCardDTO>> listarTarjetas(@PathVariable Long id);
}
