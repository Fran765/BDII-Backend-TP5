package ar.unrn.tp.domain.dto;

import ar.unrn.tp.domain.models.Client;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ClientDTO {
    @NotNull
    private Integer dni;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @Email
    private String email;
    @NotNull
    private List<CreditCardDTO> creditCards;

    public static ClientDTO fromDomain(Client client){
        return ClientDTO.builder()
                .dni(client.getDni())
                .name(client.getName())
                .surname(client.getSurname())
                .email(client.getEmail())
                .creditCards(client.getCreditCards()
                        .map(CreditCardDTO::fromDomain)
                        .collect(Collectors.toList()))
                .build();
    }
}

