package ar.unrn.tp.web.contracts;

import ar.unrn.tp.domain.dto.ClientCreateDTO;
import ar.unrn.tp.domain.dto.CreateCreditCardDTO;
import ar.unrn.tp.domain.dto.CreditCardDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

public interface ClientContract {

    @PostMapping("/crear")
    ResponseEntity<Void> crearCliente(@RequestBody @Valid ClientCreateDTO payload);

    @PutMapping("/modificar/{idClient}")
    ResponseEntity<Void> modificarCliente(@PathVariable Long idClient, @RequestBody @Valid ClientCreateDTO payload);

    @PostMapping("/agregarTarjeta/{idClient}")
    ResponseEntity<Void> agregarTarjeta(@PathVariable Long idClient, @RequestBody @Valid CreateCreditCardDTO payload);

    @GetMapping("/listarTarjetas/{idClient}")
    ResponseEntity<List<CreditCardDTO>> listarTarjetas(@PathVariable Long idClient);
}
