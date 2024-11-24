package org.example.m_citronix.mapper;

import org.example.m_citronix.dto.SaleDto;
import org.example.m_citronix.model.Harvest;
import org.example.m_citronix.model.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(source = "harvest.id", target = "harvestId")
    SaleDto toDto(Sale sale);

    @Mapping(source = "harvestId", target = "harvest")
    Sale toEntity(SaleDto saleDto);

    default Harvest mapHarvest(Long harvestId) {
        Harvest harvest = new Harvest();
        harvest.setId(harvestId);
        return harvest;
    }
}
