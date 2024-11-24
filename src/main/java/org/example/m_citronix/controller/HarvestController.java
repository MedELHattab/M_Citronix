package org.example.m_citronix.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.m_citronix.dto.HarvestDto;
import org.example.m_citronix.service.HarvestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/harvests")
@RequiredArgsConstructor
public class HarvestController {

    private final HarvestService harvestService;


    @PostMapping
    public ResponseEntity<HarvestDto> createHarvest(@RequestBody @Valid HarvestDto harvestDto) {
        HarvestDto createdHarvest = harvestService.createHarvest(harvestDto);
        return ResponseEntity.ok(createdHarvest);
    }

    @GetMapping("/{fieldId}/{season}")
    public ResponseEntity<HarvestDto> getHarvest(@PathVariable Long fieldId, @PathVariable String season) {
        HarvestDto harvest = harvestService.getHarvestByFieldAndSeason(fieldId, season);
        return ResponseEntity.ok(harvest);
    }

    @GetMapping("/{harvestId}/trees/{treeId}")
    public ResponseEntity<Double> getTreeProductivityInHarvest(
            @PathVariable Long harvestId,
            @PathVariable Long treeId) {
        double productivity = harvestService.getTreeProductivityInHarvest(harvestId, treeId);
        return ResponseEntity.ok(productivity);
    }
}
