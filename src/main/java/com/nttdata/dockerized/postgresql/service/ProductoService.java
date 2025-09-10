package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.dto.ProductoDto;
import com.nttdata.dockerized.postgresql.model.entity.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> list(Long categoriaId);
    Producto get(Long id);
    Producto create(ProductoDto dto);
    Producto update(Long id, ProductoDto dto);
    void delete(Long id);
    Producto assignCategoria(Long productoId, Long categoriaId);
}