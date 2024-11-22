package org.example.m_citronix.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.m_citronix.dto.TreeDto;
import org.example.m_citronix.mapper.TreeMapper;
import org.example.m_citronix.model.Field;
import org.example.m_citronix.model.Tree;
import org.example.m_citronix.repository.FieldRepository;
import org.example.m_citronix.repository.TreeRepository;
import org.example.m_citronix.service.TreeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TreeServiceImpl implements TreeService {
    private final TreeRepository treeRepository;
    private final FieldRepository fieldRepository;
    private final TreeMapper treeMapper;

    @Override
    public List<TreeDto> addTrees(TreeDto treeDto) {
        Field field = fieldRepository.findById(treeDto.getFieldId())
                .orElseThrow(() -> new RuntimeException("Field not found"));

        double fieldAreaHectares = field.getArea() ;
        int maxTrees = (int) (fieldAreaHectares * 100);
        int currentTreeCount = treeRepository.countByField(field);

        if (currentTreeCount + treeDto.getNumberOfTrees() > maxTrees) {
            throw new RuntimeException("Tree density exceeds the maximum allowed for the field.");
        }

        int month = treeDto.getPlantingDate().getMonthValue();
        if (month < 3 || month > 5) {
            throw new RuntimeException("Trees can only be planted between March and May.");
        }

        List<Tree> treesToSave = new ArrayList<>();
        for (int i = 0; i < treeDto.getNumberOfTrees(); i++) {
            Tree tree = treeMapper.toEntity(treeDto);
            tree.setField(field);
            treesToSave.add(tree);
        }

        List<Tree> savedTrees = treeRepository.saveAll(treesToSave);
        return savedTrees.stream().map(treeMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public List<TreeDto> getTreesInField(Long fieldId) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new RuntimeException("Field not found"));

        List<Tree> trees = treeRepository.findByField(field);
        return trees.stream().map(treeMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public double calculateTotalProductivity(Long fieldId) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new RuntimeException("Field not found"));

        List<Tree> trees = treeRepository.findByField(field);
        return trees.stream()
                .mapToDouble(Tree::getAnnualProductivity)
                .sum();
    }
}
