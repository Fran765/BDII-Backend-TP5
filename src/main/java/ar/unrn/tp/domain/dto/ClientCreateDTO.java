package ar.unrn.tp.domain.dto;

import ar.unrn.tp.domain.models.Client;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ClientCreateDTO {

    @NotNull
    private Integer dni;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String email;

    public static ClientCreateDTO fromDomain(Client client){
        return ClientCreateDTO.builder()
                .dni(client.getDni())
                .name(client.getName())
                .surname(client.getSurname())
                .email(client.getEmail())
                .build();
    }
}
