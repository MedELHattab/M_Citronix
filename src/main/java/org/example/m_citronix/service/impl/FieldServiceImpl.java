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
        Farm farm = farmRepository.findById(FarmId).orElseThrow(() -> new RuntimeException("Farm not found"));
        Field feild = fieldMapper.toEntity(feilddto) ;


        Double farmArea = farm.getArea();
        Double feildArea = feild.getArea();

        if(feildArea>(farmArea/2)){
            throw new RuntimeException("Feild area is greater 50% than farm area.");
        }


        feild.setFarm(farm);

        Field savedfeild = fieldRepository.save(feild);
        return fieldMapper.toDto(savedfeild);
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
