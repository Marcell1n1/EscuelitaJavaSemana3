package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.entity.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> list(Long categoriaId);
    Producto get(Long id);
    Producto save(Producto p);
    Producto update(Long id, Producto p);
    void delete(Long id);
    Producto assignCategoria(Long productoId, Long categoriaId);
}