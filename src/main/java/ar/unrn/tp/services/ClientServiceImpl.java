package ar.unrn.tp.services;

import ar.unrn.tp.api.ClientService;
import ar.unrn.tp.api.TransactionService;
import ar.unrn.tp.domain.dto.CreditCardDTO;
import ar.unrn.tp.domain.models.CardType;
import ar.unrn.tp.domain.models.Client;
import ar.unrn.tp.domain.models.CreditCard;
import ar.unrn.tp.exceptions.ApplicationException;
import ar.unrn.tp.exceptions.CardException;
import ar.unrn.tp.exceptions.ClientException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    @Autowired
    private final TransactionService transactionService;

    @Override
    public void crearCliente(String nombre, String apellido, Integer dni, String email) {

        this.transactionService.executeInTransaction(em -> {
            try {
                existsDniClient(em, dni);

                Client cliente = new Client(dni, nombre, apellido, email, new ArrayList<>());
                em.persist(cliente);

            } catch (Exception e) {
                throw new ClientException("Error al crear el cliente: " + e.getMessage());
            }
        });
    }

    @Override
    public void modificarCliente(Long idCliente, String nombre, String apellido, String email) {
        this.transactionService.executeInTransaction(em -> {

            try {
                Client clientDB = em.find(Client.class, idCliente);
                clientDB.updateData(nombre, apellido, email);

            } catch (NoResultException e){
                throw new ClientException("No se encontro un cliente con el DNI proporcionado: " + e.getMessage());

            } catch (Exception e){
                throw new ClientException("Error al querer actualizar datos del cliente con ID " + idCliente);
            }
        });
    }

    @Override
    public void agregarTarjeta(Long idCliente, String nro, Long idMarca) {
        this.transactionService.executeInTransaction(em -> {

            try {
                Client clientDB = em.find(Client.class, idCliente);
                CardType cardType =em.find(CardType.class,idMarca);

                clientDB.addCreditCard(new CreditCard(Long.parseLong(nro), cardType));

            } catch (NoResultException e) {
                throw new ApplicationException("No se econtraron resultados para el cliente o tipo tarjeta: " + e.getMessage());

            } catch (NonUniqueResultException e) {
                throw new ApplicationException("Se encontraron múltiples resultados para este ID de clientes o tipo tarjeta: " + e.getMessage());

            } catch (Exception e){
                throw new CardException("Error al querer agregar una tarjeta para el cliente ID " + idCliente);
            }
        });
    }

    @Override
    public List<CreditCardDTO> listarTarjetas(Long idCliente) {
        List<CreditCard> cardsByClient = new ArrayList<>();

        this.transactionService.executeInTransaction(em -> {
            try {
                Client clientDB = em.find(Client.class,idCliente);
                cardsByClient.addAll(clientDB.getCreditCards().toList());

            } catch (NoResultException e){
                throw new ClientException("Cliente con ID " + idCliente + " no encontrado: " + e.getMessage());
            }
        });

        try {

            return cardsByClient.stream()
                    .map(CreditCardDTO::fromDomain)
                    .collect(Collectors.toList());

        } catch (Exception e){
            throw new CardException("Eror al querer recuperar la lista de tarjetas para el cliente ID " + idCliente + e.getMessage());
        }
    }

    private void existsDniClient(EntityManager em, Integer dni) {
        try {
            String consultaCrear = "SELECT c FROM Client c WHERE c.dni = :dni";
            TypedQuery<Client> query = em.createQuery(consultaCrear, Client.class);
            query.setParameter("dni", dni);

            query.getSingleResult();

        } catch (NoResultException e) {
            throw new ClientException("No se encontro un cliente con el DNI proporcionado: " + e.getMessage());

        } catch (Exception e) {
            throw new ClientException("Error al querer recuperar el cliente por dni: " + e.getMessage());
        }
    }

}
