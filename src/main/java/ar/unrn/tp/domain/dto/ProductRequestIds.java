package ar.unrn.tp.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class ProductRequestIds {
    @NotNull
    private List<Long> idProducts;
}
