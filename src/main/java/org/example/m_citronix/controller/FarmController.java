package org.example.m_citronix.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.m_citronix.dto.Farmdto;
import org.example.m_citronix.dto.SearchFarmDTO;
import org.example.m_citronix.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farms")
@RequiredArgsConstructor
public class FarmController {


    private final FarmService farmService;

    @PostMapping
    public ResponseEntity<Farmdto> addFarm(@Valid @RequestBody Farmdto farmDTO) {
        Farmdto savedFarm = farmService.createFarm(farmDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFarm);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Farmdto> updateFarm(@PathVariable Long id, @RequestBody Farmdto farmDTO) {
        Farmdto updatedFarm = farmService.updateFarm(id, farmDTO);
        return ResponseEntity.ok(updatedFarm);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Farmdto> getFarmById( @PathVariable Long id) {
        return ResponseEntity.ok(farmService.getFarmById(id));
    }

    @PostMapping("/search")
    public ResponseEntity<List<Farmdto>> searchFarm(@RequestBody @Valid SearchFarmDTO searchCriteria) {
        // Pass raw DTO values to the service

        List<Farmdto> searchedFarms = farmService.searchFarms(
                searchCriteria.getName(),
                searchCriteria.getLocation(),
                searchCriteria.getMinArea(),
                searchCriteria.getMaxArea()
        );
        return ResponseEntity.ok(searchedFarms);
    }

}
