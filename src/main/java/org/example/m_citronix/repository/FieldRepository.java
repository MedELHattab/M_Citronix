package org.example.m_citronix.repository;

import org.example.m_citronix.model.Farm;
import org.example.m_citronix.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findByFarm(Farm farm);
    Double countByFarm(Farm farm);
}
