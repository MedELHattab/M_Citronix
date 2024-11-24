package org.example.m_citronix.service;


import org.example.m_citronix.dto.Feilddto;
import org.example.m_citronix.exception.NotFoundException;
import org.example.m_citronix.mapper.FieldMapper;
import org.example.m_citronix.model.Farm;
import org.example.m_citronix.model.Field;
import org.example.m_citronix.repository.FarmRepository;
import org.example.m_citronix.repository.FieldRepository;
import org.example.m_citronix.service.impl.FieldServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FieldServiceImplTest {

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FieldMapper fieldMapper;

    @InjectMocks
    private FieldServiceImpl fieldService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createField_Success() {
        // Arrange
        Long farmId = 1L;
        Double farmArea = 100.0;
        Double fieldArea = 20.0;

        Feilddto feilddto = new Feilddto();
        feilddto.setFarmId(farmId);
        feilddto.setArea(fieldArea);

        Farm farm = new Farm();
        farm.setId(farmId);
        farm.setArea(farmArea);

        Field field = new Field();
        field.setArea(fieldArea);

        when(farmRepository.findById(farmId)).thenReturn(Optional.of(farm));
        when(fieldRepository.countByFarm(farm)).thenReturn(2); // Number of fields in farm
        when(fieldRepository.findByFarm(farm)).thenReturn(Collections.emptyList());
        when(fieldMapper.toEntity(feilddto)).thenReturn(field);
        when(fieldRepository.save(field)).thenReturn(field);
        when(fieldMapper.toDto(field)).thenReturn(feilddto);

        // Act
        Feilddto result = fieldService.createField(feilddto);

        // Assert
        assertNotNull(result);
        assertEquals(farmId, result.getFarmId());
        verify(fieldRepository).save(field);
    }

    @Test
    void createField_ExceedNumberOfFields_ThrowsException() {
        // Arrange
        Long farmId = 1L;
        Feilddto feilddto = new Feilddto();
        feilddto.setFarmId(farmId);

        Farm farm = new Farm();
        farm.setId(farmId);
        farm.setArea(100.0);

        Field field = new Field();
        field.setArea(10.0);

        when(farmRepository.findById(farmId)).thenReturn(Optional.of(farm));
        when(fieldRepository.countByFarm(farm)).thenReturn(10);
        when(fieldMapper.toEntity(feilddto)).thenReturn(field); // Fix: Mock this to return a valid Field.

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> fieldService.createField(feilddto));
        assertEquals("The number of fields in a farm cannot exceed 10.", exception.getMessage());
    }


    @Test
    void getAllFieldsInFarm_Success() {
        // Arrange
        Long farmId = 1L;
        Farm farm = new Farm();
        farm.setId(farmId);

        Field field1 = new Field();
        Field field2 = new Field();

        Feilddto fieldDto1 = new Feilddto();
        Feilddto fieldDto2 = new Feilddto();

        when(farmRepository.findById(farmId)).thenReturn(Optional.of(farm));
        when(fieldRepository.findByFarm(farm)).thenReturn(Arrays.asList(field1, field2));
        when(fieldMapper.toDto(field1)).thenReturn(fieldDto1);
        when(fieldMapper.toDto(field2)).thenReturn(fieldDto2);

        // Act
        var result = fieldService.getAllFieldsInFarm(farmId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }



    @Test
    void deleteField_Success() {
        // Arrange
        Long fieldId = 1L;
        Field field = new Field();
        field.setId(fieldId);

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));

        // Act
        fieldService.deleteField(fieldId);

        // Assert
        verify(fieldRepository).delete(field);
    }

    @Test
    void deleteField_FieldNotFound_ThrowsException() {
        // Arrange
        Long fieldId = 1L;
        when(fieldRepository.findById(fieldId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> fieldService.deleteField(fieldId));
        assertEquals("Field not found", exception.getMessage());
    }

    @Test
    void updateField_Success() {
        // Arrange
        Long fieldId = 1L;
        Feilddto feilddto = new Feilddto();
        feilddto.setName("Updated Field");
        feilddto.setArea(30.0);

        Farm farm = new Farm();
        farm.setId(1L);
        farm.setArea(100.0);

        Field existingField = new Field();
        existingField.setId(fieldId);
        existingField.setName("Old Field");
        existingField.setArea(20.0);
        existingField.setFarm(farm); // Fix: Ensure the farm is set.

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(existingField));
        when(farmRepository.findById(farm.getId())).thenReturn(Optional.of(farm));
        when(fieldRepository.findByFarm(farm)).thenReturn(List.of(existingField));
        when(fieldRepository.save(any(Field.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(fieldMapper.toDto(any(Field.class))).thenReturn(feilddto);

        // Act
        Feilddto updatedField = fieldService.updateField(fieldId, feilddto);

        // Assert
        assertNotNull(updatedField);
        assertEquals("Updated Field", updatedField.getName());
    }


}

