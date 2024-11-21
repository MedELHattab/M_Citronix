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
    private Double area;

    @NotNull(message = "farm is required.")
    private Long farmId;


}
