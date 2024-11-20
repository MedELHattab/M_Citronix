package org.example.m_citronix.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@lombok.Data
public class SearchFarmDTO {

    private String name;


    private String location;

    @Positive(message = "Min area must be positive")
    private Double minArea;

    @Positive(message = "Max area must be positive")
    private Double maxArea;
}
