package org.example.m_citronix.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.m_citronix.enums.Season;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDto {

    private Long id;

    @NotNull(message = "Field ID is required.")
    private Long fieldId;

    @NotNull(message = "Season is required.")
    private Season season;

    private LocalDate harvestDate;

    private Double totalQuantity;
}
