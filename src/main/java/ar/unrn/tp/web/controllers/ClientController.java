package ar.unrn.tp.web.controllers;

import ar.unrn.tp.api.ClientService;
import ar.unrn.tp.domain.dto.ClientCreateDTO;
import ar.unrn.tp.domain.dto.CreateCreditCardDTO;
import ar.unrn.tp.domain.dto.CreditCardDTO;
import ar.unrn.tp.exceptions.ApplicationException;
import ar.unrn.tp.exceptions.CardException;
import ar.unrn.tp.exceptions.ClientException;
import ar.unrn.tp.web.contracts.ClientContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientController implements ClientContract {

    @Autowired
    private ClientService clientService;

    @Override
    public ResponseEntity<Void> crearCliente(ClientCreateDTO payload) {
        try {
            this.clientService.crearCliente(
                    payload.getName(),
                    payload.getSurname(),
                    payload.getDni(),
                    payload.getEmail()
            );

            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (ClientException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error

        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        }
    }

    @Override
    public ResponseEntity<Void> modificarCliente(Long idClient, ClientCreateDTO payload) {

        try {
            this.clientService.modificarCliente(idClient,
                    payload.getName(),
                    payload.getSurname(),
                    payload.getEmail()
            );

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (ClientException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error

        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        }
    }

    @Override
    public ResponseEntity<Void> agregarTarjeta(Long idClient, CreateCreditCardDTO payload) {
        try{
            this.clientService.agregarTarjeta(idClient,
                    String.valueOf(payload.getNumber()),
                    payload.getIdCardType()
            );

            return ResponseEntity.status(HttpStatus.ACCEPTED).build();

        } catch (ClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error

        } catch (CardException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        }
    }

    @Override
    public ResponseEntity<List<CreditCardDTO>> listarTarjetas(Long idClient) {
        try {
            List<CreditCardDTO> tarjetas = this.clientService.listarTarjetas(idClient);

            return ResponseEntity.ok(tarjetas);

        } catch (ClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error

        } catch (CardException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        }
    }
}
