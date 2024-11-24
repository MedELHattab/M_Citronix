package org.example.m_citronix.repository;

import org.example.m_citronix.model.HarvestDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, Long> {
    Optional<HarvestDetail> findByHarvestIdAndTreeId(Long harvestId, Long treeId);

}
