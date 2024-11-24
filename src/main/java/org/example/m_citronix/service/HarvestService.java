package org.example.m_citronix.service;

import org.example.m_citronix.dto.HarvestDto;

public interface HarvestService {

    HarvestDto createHarvest(HarvestDto harvestDto);

    HarvestDto getHarvestByFieldAndSeason(Long fieldId, String season);

    double getTreeProductivityInHarvest(Long harvestId, Long treeId);

}
