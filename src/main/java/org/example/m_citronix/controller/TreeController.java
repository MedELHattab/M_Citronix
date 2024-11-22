package org.example.m_citronix.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.m_citronix.dto.TreeDto;
import org.example.m_citronix.service.TreeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trees")
@RequiredArgsConstructor
public class TreeController {

    private final TreeService treeService;

    @PostMapping
    public ResponseEntity<List<TreeDto>> addTree(@RequestBody @Valid TreeDto treeDto) {
        return ResponseEntity.ok(treeService.addTrees(treeDto));
    }

    @GetMapping("/field/{id}")
    public ResponseEntity<List<TreeDto>> getTrees(@PathVariable Long id) {
        List<TreeDto> trees = treeService.getTreesInField(id);
        return ResponseEntity.ok(trees);
    }



}
