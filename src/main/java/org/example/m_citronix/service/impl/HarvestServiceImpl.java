package org.example.m_citronix.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.m_citronix.dto.HarvestDto;
import org.example.m_citronix.mapper.HarvestMapper;
import org.example.m_citronix.model.Field;
import org.example.m_citronix.model.Harvest;
import org.example.m_citronix.enums.Season;
import org.example.m_citronix.model.HarvestDetail;
import org.example.m_citronix.model.Tree;
import org.example.m_citronix.repository.FieldRepository;
import org.example.m_citronix.repository.HarvestDetailRepository;
import org.example.m_citronix.repository.HarvestRepository;
import org.example.m_citronix.repository.TreeRepository;
import org.example.m_citronix.service.HarvestService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final FieldRepository fieldRepository;
    private final HarvestMapper harvestMapper;
    private final TreeRepository treeRepository;
    private final HarvestDetailRepository harvestDetailRepository;


    @Override
    public HarvestDto createHarvest(HarvestDto harvestDto) {
        // Fetch the field associated with the harvest
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
            throw new RuntimeException("There are no trees in this field.");
        }

        // Calculate totalQuantity as the sum of annual productivity of all trees in the field
        double totalQuantity = trees.stream()
                .mapToDouble(Tree::getAnnualProductivity)
                .sum();

        // Create the Harvest entity
        Harvest harvest = harvestMapper.toEntity(harvestDto);
        harvest.setField(field);
        harvest.setHarvestDate(LocalDate.now());
        harvest.setTotalQuantity(totalQuantity); // Set calculated totalQuantity

        // Save the Harvest entity
        Harvest savedHarvest = harvestRepository.save(harvest);

        // Save details of each tree into the HarvestDetail table
        List<HarvestDetail> harvestDetails = trees.stream()
                .map(tree -> {
                    HarvestDetail detail = new HarvestDetail();
                    detail.setHarvest(savedHarvest);
                    detail.setTree(tree);
                    detail.setAnnualProductivity(tree.getAnnualProductivity());
                    return detail;
                })
                .collect(Collectors.toList());

        harvestDetailRepository.saveAll(harvestDetails);

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
        // Fetch the HarvestDetail for the given harvest and tree IDs
        HarvestDetail harvestDetail = harvestDetailRepository.findByHarvestIdAndTreeId(harvestId, treeId)
                .orElseThrow(() -> new RuntimeException("No productivity record found for the given tree and harvest."));

        // Return the annual productivity recorded in HarvestDetail
        return harvestDetail.getAnnualProductivity();
    }

}
