package org.example.m_citronix.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeDto {

    private Long id;

    @NotNull(message = "Planting date is required.")
    private LocalDate plantingDate;

    @NotNull(message = "name is required.")
    private String name;

    @NotNull(message = "Field ID is required.")
    private Long fieldId;

    @NotNull(message = "Number of trees is required.")
    private Integer numberOfTrees;

    private int age; // New field
    private double annualProductivity;
}
