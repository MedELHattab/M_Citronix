package org.example.m_citronix.repository;

import org.example.m_citronix.model.Farm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FarmSearchRepository {


    List<Farm> searchFarms(String name, String location, Double minArea, Double maxArea);
}
