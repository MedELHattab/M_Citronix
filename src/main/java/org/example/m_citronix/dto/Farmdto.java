package org.example.m_citronix.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Farmdto {

    private Long id;

    @NotNull(message = "The name is required.")
    private String name;

    @NotNull(message = "The location is required.")
    private String location;

    private Double area;
}

