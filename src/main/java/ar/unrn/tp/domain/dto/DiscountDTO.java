package ar.unrn.tp.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DiscountDTO {
    @NotNull
    private float percent;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;

    public DiscountDTO(float percent, LocalDate startDate, LocalDate endDate) {
        this.percent = percent;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
