package org.example.m_citronix.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.m_citronix.dto.Feilddto;
import org.example.m_citronix.service.FieldService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/fields")
public class FieldController {
    private final FieldService fieldService;

    @GetMapping("/{id}")
     public ResponseEntity<Feilddto> getField(@PathVariable Long id) {
        return ResponseEntity.ok(fieldService.getField(id));
    }

    @GetMapping
    public ResponseEntity<List<Feilddto>> getFields() {
        return ResponseEntity.ok(fieldService.getAllFields());
    }

    @PostMapping("/{id}")
    public ResponseEntity<Feilddto> deleteField(@PathVariable Long id) {
        fieldService.deleteField(id);
        return ResponseEntity.ok(fieldService.getField(id));
    }

    @PostMapping
    public ResponseEntity<Feilddto> createField(@RequestBody @Valid Feilddto f) {
        return ResponseEntity.ok(fieldService.createField(f));
    }

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<List<Feilddto>> getFieldsInFarm(@PathVariable Long farmId) {
        return ResponseEntity.ok(fieldService.getAllFieldsInFarm(farmId));
    }
}
