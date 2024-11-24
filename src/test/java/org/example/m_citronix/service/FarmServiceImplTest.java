package org.example.m_citronix.service;

import org.example.m_citronix.dto.Farmdto;
import org.example.m_citronix.exception.NotFoundException;
import org.example.m_citronix.mapper.FarmMapper;
import org.example.m_citronix.model.Farm;
import org.example.m_citronix.repository.FarmRepository;
import org.example.m_citronix.repository.FarmSearchRepository;
import org.example.m_citronix.service.impl.FarmServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FarmServiceImplTest {

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FarmMapper farmMapper;

    @Mock
    private FarmSearchRepository farmSearchRepository;

    @InjectMocks
    private FarmServiceImpl farmService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFarm() {
        // Arrange
        Farmdto farmDto = new Farmdto();
        farmDto.setName("Farm 1");

        Farm farm = new Farm();
        farm.setName("Farm 1");

        when(farmMapper.toEntity(farmDto)).thenReturn(farm);
        when(farmRepository.save(farm)).thenReturn(farm);
        when(farmMapper.toDTO(farm)).thenReturn(farmDto);

        // Act
        Farmdto result = farmService.createFarm(farmDto);

        // Assert
        assertEquals(farmDto.getName(), result.getName());
        verify(farmRepository, times(1)).save(farm);
        verify(farmMapper, times(1)).toEntity(farmDto);
        verify(farmMapper, times(1)).toDTO(farm);
    }

    @Test
    void testGetAllFarms() {
        // Arrange
        Farm farm = new Farm();
        farm.setName("Farm 1");

        Farmdto farmDto = new Farmdto();
        farmDto.setName("Farm 1");

        when(farmRepository.findAll()).thenReturn(List.of(farm));
        when(farmMapper.toDTO(farm)).thenReturn(farmDto);

        // Act
        List<Farmdto> result = farmService.getAllFarms();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Farm 1", result.get(0).getName());
        verify(farmRepository, times(1)).findAll();
        verify(farmMapper, times(1)).toDTO(farm);
    }

    @Test
    void testGetFarmById_Found() {
        // Arrange
        Farm farm = new Farm();
        farm.setId(1L);
        farm.setName("Farm 1");

        Farmdto farmDto = new Farmdto();
        farmDto.setId(1L);
        farmDto.setName("Farm 1");

        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(farmMapper.toDTO(farm)).thenReturn(farmDto);

        // Act
        Farmdto result = farmService.getFarmById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(farmRepository, times(1)).findById(1L);
        verify(farmMapper, times(1)).toDTO(farm);
    }

    @Test
    void testGetFarmById_NotFound() {
        // Arrange
        when(farmRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> farmService.getFarmById(1L));
        assertEquals("Farm not found with id 1", exception.getMessage());
        verify(farmRepository, times(1)).findById(1L);
        verifyNoInteractions(farmMapper);
    }

    @Test
    void testUpdateFarm_Found() {
        // Arrange
        Farm farm = new Farm();
        farm.setId(1L);
        farm.setName("Old Name");

        Farmdto farmDto = new Farmdto();
        farmDto.setName("New Name");

        Farm updatedFarm = new Farm();
        updatedFarm.setId(1L);
        updatedFarm.setName("New Name");

        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(farmRepository.save(farm)).thenReturn(updatedFarm);
        when(farmMapper.toDTO(updatedFarm)).thenReturn(farmDto);

        // Act
        Farmdto result = farmService.updateFarm(1L, farmDto);

        // Assert
        assertEquals("New Name", result.getName());
        verify(farmRepository, times(1)).findById(1L);
        verify(farmRepository, times(1)).save(farm);
        verify(farmMapper, times(1)).toDTO(updatedFarm);
    }

    @Test
    void testDeleteFarm_Found() {
        // Arrange
        when(farmRepository.existsById(1L)).thenReturn(true);

        // Act
        farmService.deleteFarm(1L);

        // Assert
        verify(farmRepository, times(1)).existsById(1L);
        verify(farmRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteFarm_NotFound() {
        // Arrange
        when(farmRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> farmService.deleteFarm(1L));
        assertEquals("Farm not found with id 1", exception.getMessage());
        verify(farmRepository, times(1)).existsById(1L);
        verify(farmRepository, never()).deleteById(anyLong());
    }

    @Test
    void testSearchFarms_NoResults() {
        // Arrange
        when(farmSearchRepository.searchFarms("name", "location", 1.0, 10.0)).thenReturn(Collections.emptyList());

        // Act & Assert
        Exception exception = assertThrows(NotFoundException.class, () ->
                farmService.searchFarms("name", "location", 1.0, 10.0));
        assertEquals("No farms found matching the search criteria.", exception.getMessage());
        verify(farmSearchRepository, times(1)).searchFarms("name", "location", 1.0, 10.0);
    }
}
