package org.example.m_citronix.repository;

import org.example.m_citronix.model.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FarmRepository extends JpaRepository<Farm, Long> {
}
