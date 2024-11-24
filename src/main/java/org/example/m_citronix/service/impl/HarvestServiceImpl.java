package org.example.m_citronix.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.m_citronix.dto.HarvestDto;
import org.example.m_citronix.dto.TreeDto;
import org.example.m_citronix.mapper.HarvestMapper;
import org.example.m_citronix.model.Field;
import org.example.m_citronix.model.Harvest;
import org.example.m_citronix.enums.Season;
import org.example.m_citronix.model.Tree;
import org.example.m_citronix.repository.FieldRepository;
import org.example.m_citronix.repository.HarvestRepository;
import org.example.m_citronix.repository.TreeRepository;
import org.example.m_citronix.service.HarvestService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final FieldRepository fieldRepository;
    private final HarvestMapper harvestMapper;
    private final TreeRepository treeRepository;


    @Override
    public HarvestDto createHarvest(HarvestDto harvestDto) {
        Field field = fieldRepository.findById(harvestDto.getFieldId())
                .orElseThrow(() -> new RuntimeException("Field not found"));

        int currentYear = LocalDate.now().getYear();

        // Check if a harvest already exists for the field, season, and year
        if (harvestRepository.existsByFieldAndSeasonAndYear(field, harvestDto.getSeason(), currentYear)) {
            throw new RuntimeException("A harvest already exists for this field, season, and year.");
        }

        // Fetch all trees in the field
        List<Tree> trees = treeRepository.findByField(field);

        if (trees.isEmpty()) {
            throw new RuntimeException("there are no trees in this field.");
        }
        // Calculate totalQuantity as the sum of annual productivity of all trees in the field
        double totalQuantity = trees.stream()
                .mapToDouble(Tree::getAnnualProductivity)
                .sum();
//        System.out.println(totalQuantity);
        // Create the Harvest entity
        Harvest harvest = harvestMapper.toEntity(harvestDto);
        harvest.setField(field);
        harvest.setHarvestDate(LocalDate.now());
        harvest.setTotalQuantity(totalQuantity); // Set calculated totalQuantity

        // Save the Harvest entity
        Harvest savedHarvest = harvestRepository.save(harvest);

        // Return the DTO
        return harvestMapper.toDto(savedHarvest);
    }


    @Override
    public HarvestDto getHarvestByFieldAndSeason(Long fieldId, String season) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new RuntimeException("Field not found"));

        Harvest harvest = harvestRepository.findByFieldAndSeason(field, Season.valueOf(season.toUpperCase()))
                .orElseThrow(() -> new RuntimeException("Harvest not found for the specified field and season."));

        return harvestMapper.toDto(harvest);
    }

    @Override
    public double getTreeProductivityInHarvest(Long harvestId, Long treeId) {
        // Check if the harvest exists
        Harvest harvest = harvestRepository.findById(harvestId)
                .orElseThrow(() -> new RuntimeException("Harvest not found"));

        // Check if the tree exists in the field associated with this harvest
        Tree tree = treeRepository.findById(treeId)
                .orElseThrow(() -> new RuntimeException("Tree not found"));

        if (!tree.getField().equals(harvest.getField())) {
            throw new RuntimeException("Tree does not belong to the field of the specified harvest.");
        }
        // Return the productivity of the tree
        return tree.getAnnualProductivity();
    }
}
