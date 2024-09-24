package ar.unrn.tp.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class DiscountDTO {
    @NotNull
    private float percent;
    @NotBlank
    private LocalDate startDate;
    @NotBlank
    private LocalDate endDate;

}
