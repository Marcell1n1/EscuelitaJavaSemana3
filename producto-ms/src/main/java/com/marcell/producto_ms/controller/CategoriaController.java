package com.marcell.producto_ms.controller;

import com.marcell.producto_ms.mapper.CatalogoMapper;
import com.marcell.producto_ms.model.dto.CategoriaDto;
import com.marcell.producto_ms.model.entity.Categoria;
import com.marcell.producto_ms.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService service;

    public CategoriaController(CategoriaService service){
        this.service = service;
    }

    @GetMapping public ResponseEntity<List<CategoriaDto>> list(){
        return ResponseEntity.ok(CatalogoMapper.INSTANCE.toDtoCategorias(service.list()));
    }

    @GetMapping("/{id}") public ResponseEntity<CategoriaDto> get(@PathVariable Long id){
        return ResponseEntity.ok(CatalogoMapper.INSTANCE.toDto(service.get(id)));
    }

    @PostMapping public ResponseEntity<CategoriaDto> create(@RequestBody CategoriaDto dto){
        return ResponseEntity.ok(CatalogoMapper.INSTANCE.toDto(service.save(CatalogoMapper.INSTANCE.toEntity(dto))));
    }

    @PutMapping("/{id}") public ResponseEntity<CategoriaDto> update(@PathVariable Long id, @RequestBody CategoriaDto dto){
        Categoria in = CatalogoMapper.INSTANCE.toEntity(dto);
        return ResponseEntity.ok(CatalogoMapper.INSTANCE.toDto(service.update(id, in)));
    }

    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id); return ResponseEntity.noContent().build();
    }
}
