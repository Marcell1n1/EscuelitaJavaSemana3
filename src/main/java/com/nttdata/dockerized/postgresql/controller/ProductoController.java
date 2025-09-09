package com.nttdata.dockerized.postgresql.controller;

import com.nttdata.dockerized.postgresql.model.dto.ProductoDto;
import com.nttdata.dockerized.postgresql.model.entity.Producto;
import com.nttdata.dockerized.postgresql.repository.CategoriaRepository;
import com.nttdata.dockerized.postgresql.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.nttdata.dockerized.postgresql.mapper.CatalogoMapper.INSTANCE;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service; private final CategoriaRepository catRepo;
    public ProductoController(ProductoService s, CategoriaRepository c){
        this.service=s;
        this.catRepo=c;
    }

    @GetMapping
    public ResponseEntity<List<ProductoDto>> list(@RequestParam(required=false) Long categoriaId){
        return ResponseEntity.ok(INSTANCE.toDtoProductos(service.list(categoriaId)));
    }

    @GetMapping("/{id}") public ResponseEntity<ProductoDto> get(@PathVariable Long id){
        return ResponseEntity.ok(INSTANCE.toDto(service.get(id)));
    }

    @PostMapping public ResponseEntity<ProductoDto> create(@RequestBody ProductoDto dto){
        Producto p = INSTANCE.toEntity(dto);
        p.setCategoria(catRepo.findById(dto.getCategoriaId()).orElseThrow());
        Producto saved = service.save(p);
        return ResponseEntity.ok(INSTANCE.toDto(saved));
    }

    @PutMapping("/{id}") public ResponseEntity<ProductoDto> update(@PathVariable Long id, @RequestBody ProductoDto dto){
        Producto in = INSTANCE.toEntity(dto);
        if(dto.getCategoriaId()!=null){
            in.setCategoria(catRepo.findById(dto.getCategoriaId()).orElseThrow());
        }
        return ResponseEntity.ok(INSTANCE.toDto(service.update(id, in)));
    }

    @PutMapping("/{id}/categoria/{categoriaId}")
    public ResponseEntity<ProductoDto> assignCategoria(@PathVariable Long id, @PathVariable Long categoriaId){
        return ResponseEntity.ok(INSTANCE.toDto(service.assignCategoria(id, categoriaId)));
    }

    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id); return ResponseEntity.noContent().build();
    }
}
