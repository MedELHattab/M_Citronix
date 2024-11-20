package org.example.m_citronix.service;

import org.example.m_citronix.dto.Farmdto;

import java.util.List;

public interface FarmService {
    Farmdto createFarm(Farmdto farmDTO);

    List<Farmdto> getAllFarms();

    Farmdto getFarmById(Long id);

    Farmdto updateFarm(Long id, Farmdto farmDTO);

    void deleteFarm(Long id);
}
