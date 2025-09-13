package com.marcell.producto_ms.service;

import com.marcell.producto_ms.model.dto.ProductoDto;
import com.marcell.producto_ms.model.entity.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> list(Long categoriaId);
    Producto get(Long id);
    Producto create(ProductoDto dto);
    Producto update(Long id, ProductoDto dto);
    void delete(Long id);
    Producto assignCategoria(Long productoId, Long categoriaId);
}