package org.example.m_citronix.mapper;

import org.example.m_citronix.dto.HarvestDto;
import org.example.m_citronix.model.Field;
import org.example.m_citronix.model.Harvest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HarvestMapper {

    @Mapping(source = "field.id", target = "fieldId")
    HarvestDto toDto(Harvest harvest);

    @Mapping(source = "fieldId", target = "field")
    Harvest toEntity(HarvestDto harvestDto);

    default Field map(Long fieldId) {
        Field field = new Field();
        field.setId(fieldId);
        return field;
    }

    default Long map(Field field) {
        return field != null ? field.getId() : null;
    }
}
