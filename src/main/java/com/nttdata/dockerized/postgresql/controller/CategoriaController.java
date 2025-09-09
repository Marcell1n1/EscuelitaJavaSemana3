package com.nttdata.dockerized.postgresql.controller;

import com.nttdata.dockerized.postgresql.model.dto.CategoriaDto;
import com.nttdata.dockerized.postgresql.model.entity.Categoria;
import com.nttdata.dockerized.postgresql.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.nttdata.dockerized.postgresql.mapper.CatalogoMapper.INSTANCE;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService service;

    public CategoriaController(CategoriaService service){
        this.service = service;
    }

    @GetMapping public ResponseEntity<List<CategoriaDto>> list(){
        return ResponseEntity.ok(INSTANCE.toDtoCategorias(service.list()));
    }

    @GetMapping("/{id}") public ResponseEntity<CategoriaDto> get(@PathVariable Long id){
        return ResponseEntity.ok(INSTANCE.toDto(service.get(id)));
    }

    @PostMapping public ResponseEntity<CategoriaDto> create(@RequestBody CategoriaDto dto){
        return ResponseEntity.ok(INSTANCE.toDto(service.save(INSTANCE.toEntity(dto))));
    }

    @PutMapping("/{id}") public ResponseEntity<CategoriaDto> update(@PathVariable Long id, @RequestBody CategoriaDto dto){
        Categoria in = INSTANCE.toEntity(dto);
        return ResponseEntity.ok(INSTANCE.toDto(service.update(id, in)));
    }

    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id); return ResponseEntity.noContent().build();
    }
}
