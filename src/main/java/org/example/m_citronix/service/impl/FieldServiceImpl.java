package org.example.m_citronix.service.impl;


import lombok.RequiredArgsConstructor;
import org.example.m_citronix.dto.Feilddto;
import org.example.m_citronix.mapper.FieldMapper;
import org.example.m_citronix.model.Farm;
import org.example.m_citronix.model.Field;
import org.example.m_citronix.repository.FarmRepository;
import org.example.m_citronix.repository.FieldRepository;
import org.example.m_citronix.service.FieldService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final FieldMapper fieldMapper;
    private final FarmRepository farmRepository;

    @Override
    public Feilddto createField(Feilddto feilddto) {

        Long FarmId = feilddto.getFarmId();
        Farm farm = farmRepository.findById(FarmId)
                .orElseThrow(() -> new RuntimeException("Farm not found"));

        Field feild = fieldMapper.toEntity(feilddto);

        Double farmArea = farm.getArea();
        Double feildArea = feild.getArea();

        // Count number of fields in the farm
        int numberOfFieldsInFarm = fieldRepository.countByFarm(farm);
        if (numberOfFieldsInFarm >= 10) {
            throw new RuntimeException("The number of fields in a farm cannot exceed 10.");
        }

        // Calculate the total area of existing fields
        Double totalFieldArea = fieldRepository.findByFarm(farm).stream()
                .mapToDouble(Field::getArea)
                .sum();

        // Check if the new field's area exceeds 50% of the farm's area
        if (feildArea > (farmArea / 2)) {
            throw new RuntimeException("Field area is greater than 50% of the farm's area.");
        }

        // Check if the sum of existing areas + new field's area exceeds the farm's area
        if (totalFieldArea + feildArea > farmArea) {
            throw new RuntimeException("The total area of fields cannot exceed the farm's area.");
        }

        feild.setFarm(farm);

        Field savedField = fieldRepository.save(feild);
        return fieldMapper.toDto(savedField);
    }

    @Override
    public List<Feilddto> getAllFieldsInFarm(Long farmId) {
        Farm farm = farmRepository.findById(farmId).orElseThrow(() -> new RuntimeException("Farm not found"));
        List<Field> fields = fieldRepository.findByFarm(farm);
        if (fields.isEmpty()) {
            throw new RuntimeException("No fields found for the given farm.");
        }
        return fields.stream()
                .map(fieldMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public Feilddto getField(Long id) {
        Field field = fieldRepository.findById(id).orElseThrow(()-> new RuntimeException("Field not found"));
        return fieldMapper.toDto(field);
    }

    @Override
    public List<Feilddto> getAllFields() {
        return  fieldRepository.findAll()
                .stream()
                .map(fieldMapper::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public Feilddto updateField(Long id ,Feilddto feilddto) {
        Field existingField = fieldRepository.findById(id).orElseThrow(()-> new RuntimeException("Field not found"));
        // Merge new values with existing values
        if (feilddto.getName() != null) {
            existingField.setName(feilddto.getName());
        }

        if (feilddto.getArea() != null) {
            existingField.setArea(feilddto.getArea());
        }

        // Save and return updated entity
        Field updatedField = fieldRepository.save(existingField);
        return fieldMapper.toDto(updatedField);
    }

    @Override
    public void deleteField(Long id) {
        Field field = fieldRepository.findById(id).orElseThrow(()-> new RuntimeException("Field not found"));
        fieldRepository.delete(field);
    }

}
