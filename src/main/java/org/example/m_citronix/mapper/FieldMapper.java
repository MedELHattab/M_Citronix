package org.example.m_citronix.mapper;

import org.example.m_citronix.dto.Feilddto;
import org.example.m_citronix.model.Field;
import org.example.m_citronix.model.Farm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FieldMapper {

    @Mapping(source = "farm.id", target = "farmId") // Map farm's id to farmId in DTO
    Feilddto toDto(Field field);

    @Mapping(source = "farmId", target = "farm") // Map farmId in DTO to farm entity
    Field toEntity(Feilddto feilddto);

    // Custom mapping for farm
    default Long map(Farm farm) {
        return farm != null ? farm.getId() : null;
    }

    default Farm map(Long farmId) {
        Farm farm = new Farm();
        farm.setId(farmId);
        return farm;
    }
}
