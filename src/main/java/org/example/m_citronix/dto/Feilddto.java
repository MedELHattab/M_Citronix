package org.example.m_citronix.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feilddto {

    private Long id;

    @NotNull(message = "The name is required.")
    private String name;


    @NotNull(message = "The area is required.")
    @Min(value = 1000, message = "The area must be at least 0.1 hectares (1,000 m²).")
    private Double area;

    @NotNull(message = "farm is required.")
    private Long farmId;


}
