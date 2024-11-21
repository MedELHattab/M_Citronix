package org.example.m_citronix.service;

import org.example.m_citronix.dto.Feilddto;

import java.util.List;

public interface FieldService {

    Feilddto getField(Long id);

    Feilddto createField(Feilddto f);
    Feilddto updateField(Long id, Feilddto f);
    void deleteField(Long id);
    List<Feilddto> getAllFields();
     List<Feilddto> getAllFieldsInFarm(Long farmId);
}
