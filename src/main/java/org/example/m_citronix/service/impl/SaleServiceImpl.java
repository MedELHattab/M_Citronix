package org.example.m_citronix.service.impl;

import org.example.m_citronix.dto.SaleDto;
import org.example.m_citronix.mapper.SaleMapper;
import org.example.m_citronix.model.Harvest;
import org.example.m_citronix.model.Sale;
import org.example.m_citronix.repository.HarvestRepository;
import org.example.m_citronix.repository.SaleRepository;
import org.example.m_citronix.service.SaleService;
import org.springframework.stereotype.Service;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final HarvestRepository harvestRepository;
    private final SaleMapper saleMapper;

    public SaleServiceImpl(SaleRepository saleRepository,
                           HarvestRepository harvestRepository,
                           SaleMapper saleMapper) {
        this.saleRepository = saleRepository;
        this.harvestRepository = harvestRepository;
        this.saleMapper = saleMapper;
    }

    @Override
    public SaleDto createSale(SaleDto saleDto) {

        Harvest harvest = harvestRepository.findById(saleDto.getHarvestId())
                .orElseThrow(() -> new RuntimeException("Harvest not found"));

        Sale sale = saleMapper.toEntity(saleDto);
        sale.setHarvest(harvest);

         sale.setQuantity(harvest.getTotalQuantity());

        Sale savedSale = saleRepository.save(sale);

        return saleMapper.toDto(savedSale);
    }
}
