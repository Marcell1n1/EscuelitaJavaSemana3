package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.entity.Categoria;
import com.nttdata.dockerized.postgresql.repository.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository repo;

    public CategoriaServiceImpl(CategoriaRepository repo){
        this.repo = repo;
    }

    public List<Categoria> list(){
        return repo.findAll();
    }

    public Categoria get(Long id){
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La Categoria no se encontro!"));
    }

    public Categoria save(Categoria c){
        return repo.save(c);
    }

    public Categoria update(Long id, Categoria in){
        Categoria db = get(id);
        if(in.getNombre()!=null) db.setNombre(in.getNombre());
        if(in.getDescripcion()!=null) db.setDescripcion(in.getDescripcion());
        return repo.save(db);
    }

    public void delete(Long id){
        if(!repo.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La Categoria no se encontro!");
        }
        repo.deleteById(id);
    }
}
