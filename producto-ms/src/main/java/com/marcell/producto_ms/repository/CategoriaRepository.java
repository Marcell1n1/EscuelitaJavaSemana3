package com.marcell.producto_ms.repository;

import com.marcell.producto_ms.model.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}