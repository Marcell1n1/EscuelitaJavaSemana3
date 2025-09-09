package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.entity.Categoria;
import java.util.List;

public interface CategoriaService {
    List<Categoria> list();
    Categoria get(Long id);
    Categoria save(Categoria c);
    Categoria update(Long id, Categoria c);
    void delete(Long id);
}