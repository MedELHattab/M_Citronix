package org.example.m_citronix.service;

import org.example.m_citronix.dto.TreeDto;

import java.util.List;

public interface TreeService {

    List addTrees(TreeDto tree);
    List<TreeDto> getTreesInField(Long fieldId);
    double calculateTotalProductivity(Long fieldId);
}
