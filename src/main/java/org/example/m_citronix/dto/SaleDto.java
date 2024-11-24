package org.example.m_citronix.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SaleDto {
    private Long id;
    private LocalDate saleDate;

    @NotNull(message = "The unit price is required.")
    private double unitPrice;
    private double quantity;
    private double revenue;

    @NotNull(message = "The client name is required.")
    private String clientName;

    @NotNull(message = "The harvest is required.")
    private Long harvestId;
}
