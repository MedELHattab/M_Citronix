package org.example.m_citronix.mapper;

import org.example.m_citronix.dto.Farmdto;
import org.example.m_citronix.model.Farm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FarmMapper {

    Farm toEntity(Farmdto farmDTO);

    Farmdto toDTO(Farm farm);
}

