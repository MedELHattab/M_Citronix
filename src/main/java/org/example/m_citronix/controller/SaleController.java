package org.example.m_citronix.controller;

import org.example.m_citronix.dto.SaleDto;
import org.example.m_citronix.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity<SaleDto> createSale(@RequestBody SaleDto saleDto) {
        SaleDto createdSale = saleService.createSale(saleDto);
        return ResponseEntity.ok(createdSale);
    }
}
