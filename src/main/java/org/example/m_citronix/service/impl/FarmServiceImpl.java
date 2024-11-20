package org.example.m_citronix.service.impl;


import org.example.m_citronix.dto.Farmdto;
import org.example.m_citronix.exception.NotFoundException;
import org.example.m_citronix.mapper.FarmMapper;
import org.example.m_citronix.model.Farm;
import org.example.m_citronix.repository.FarmRepository;
import org.example.m_citronix.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FarmServiceImpl implements FarmService {

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private FarmMapper farmMapper;

    @Override
    public Farmdto createFarm(Farmdto farmDTO) {
        Farm farm = farmMapper.toEntity(farmDTO);
        Farm savedFarm = farmRepository.save(farm);
        return farmMapper.toDTO(savedFarm);
    }

    @Override
    public List<Farmdto> getAllFarms() {
        return farmRepository.findAll()
                .stream()
                .map(farmMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Farmdto getFarmById(Long id) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farm not found with id " + id));
        return farmMapper.toDTO(farm);
    }

    @Override
    public Farmdto updateFarm(Long id, Farmdto farmDTO) {
        Farm existingFarm = farmRepository.findById(id).orElseThrow(() -> new NotFoundException("Farm not found with ID: " + id));

        // Merge new values with existing values
        if (farmDTO.getName() != null) {
            existingFarm.setName(farmDTO.getName());
        }
        if (farmDTO.getLocation() != null) {
            existingFarm.setLocation(farmDTO.getLocation());
        }
        if (farmDTO.getArea() != null) {
            existingFarm.setArea(farmDTO.getArea());
        }

        // Save and return updated entity
        Farm updatedFarm = farmRepository.save(existingFarm);
        return farmMapper.toDTO(updatedFarm);
    }


    @Override
    public void deleteFarm(Long id) {
        if (!farmRepository.existsById(id)) {
            throw new RuntimeException("Farm not found with id " + id);
        }
        farmRepository.deleteById(id);
    }
}

