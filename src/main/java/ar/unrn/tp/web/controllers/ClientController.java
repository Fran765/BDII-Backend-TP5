package ar.unrn.tp.web.controllers;

import ar.unrn.tp.api.ClientService;
import ar.unrn.tp.domain.dto.ClientDTO;
import ar.unrn.tp.domain.dto.CreditCardDTO;
import ar.unrn.tp.web.contracts.ClientContract;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientController implements ClientContract {

    private final ClientService clientService;

    @Override
    public ResponseEntity<Void> crearCliente(ClientDTO payload) {

        this.clientService.crearCliente(
                payload.getName(),
                payload.getSurname(),
                payload.getDni(),
                payload.getEmail()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> modificarCliente(Long id, ClientDTO payload) {

        this.clientService.modificarCliente(id,
                payload.getName(),
                payload.getSurname(),
                payload.getEmail()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> agregarTarjeta(Long id, CreditCardDTO payload) {

        this.clientService.agregarTarjeta(id,
                String.valueOf(payload.getNumber()),
                payload.getType().getId()
        );
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Override
    public ResponseEntity<List<CreditCardDTO>> listarTarjetas(Long id) {
        List<CreditCardDTO> tarjetas = this.clientService.listarTarjetas(id);

        return ResponseEntity.ok(tarjetas);
    }
}
