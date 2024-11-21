package org.example.m_citronix.repository;

import org.example.m_citronix.model.Field;
import org.example.m_citronix.model.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreeRepository extends JpaRepository<Tree, Long> {
    List<Tree> findByField(Field field);
    int countByField(Field field);
}
