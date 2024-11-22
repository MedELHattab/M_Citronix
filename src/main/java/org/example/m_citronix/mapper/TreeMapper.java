package org.example.m_citronix.mapper;

import org.example.m_citronix.dto.TreeDto;
import org.example.m_citronix.model.Field;
import org.example.m_citronix.model.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TreeMapper {

    @Mapping(source = "field.id", target = "fieldId")
    @Mapping(target = "age", expression = "java(tree.getAge())")
    @Mapping(target = "annualProductivity", expression = "java(tree.getAnnualProductivity())")
    TreeDto toDto(Tree tree);

    @Mapping(source = "fieldId", target = "field")
    Tree toEntity(TreeDto treeDto);

    default Field map(Long fieldId) {
        Field field = new Field();
        field.setId(fieldId);
        return field;
    }

    default Long map(Field field) {
        return field != null ? field.getId() : null;
    }
}
