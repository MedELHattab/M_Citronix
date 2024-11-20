package org.example.m_citronix.controller;

import jakarta.validation.Valid;
import org.example.m_citronix.dto.Farmdto;
import org.example.m_citronix.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
    @RequestMapping("/api/farms")
public class FarmController {


    @Autowired
    private FarmService farmService;

    @PostMapping
    public ResponseEntity<Farmdto> addFarm(@Valid @RequestBody Farmdto farmDTO) {
        Farmdto savedFarm = farmService.createFarm(farmDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFarm);
    }

}
