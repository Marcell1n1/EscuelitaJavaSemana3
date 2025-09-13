package com.marcell.producto_ms.controller;

import com.marcell.producto_ms.mapper.CatalogoMapper;
import com.marcell.producto_ms.model.dto.ProductoDto;
import com.marcell.producto_ms.model.entity.Producto;
import com.marcell.producto_ms.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;
    public ProductoController(ProductoService service){ this.service = service; }

    @GetMapping
    public List<ProductoDto> list(@RequestParam(required=false) Long categoriaId){
        List<Producto> ps = service.list(categoriaId);
        return CatalogoMapper.INSTANCE.toDtoProductos(ps);
    }

    @GetMapping("/{id}")
    public ProductoDto get(@PathVariable Long id){
        return CatalogoMapper.INSTANCE.toDto(service.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoDto create(@RequestBody ProductoDto dto){
        return CatalogoMapper.INSTANCE.toDto(service.create(dto));
    }

    @PutMapping("/{id}")
    public ProductoDto update(@PathVariable Long id, @RequestBody ProductoDto dto){
        return CatalogoMapper.INSTANCE.toDto(service.update(id, dto));
    }

    @PutMapping("/{id}/categoria/{categoriaId}")
    public ProductoDto assignCategoria(@PathVariable Long id, @PathVariable Long categoriaId){
        return CatalogoMapper.INSTANCE.toDto(service.assignCategoria(id, categoriaId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}