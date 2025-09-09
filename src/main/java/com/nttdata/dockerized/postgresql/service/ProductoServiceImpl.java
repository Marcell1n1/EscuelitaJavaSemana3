package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.entity.Categoria;
import com.nttdata.dockerized.postgresql.model.entity.Producto;
import com.nttdata.dockerized.postgresql.repository.CategoriaRepository;
import com.nttdata.dockerized.postgresql.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repo;
    private final CategoriaRepository catRepo;

    public ProductoServiceImpl(ProductoRepository repo, CategoriaRepository catRepo) {
        this.repo = repo; this.catRepo = catRepo;
    }

    public List<Producto> list(Long categoriaId){
        return (categoriaId != null) ? repo.findByCategoria_Id(categoriaId) : repo.findAll();
    }

    public Producto get(Long id){
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El Producto no se encontro!"));
    }

    public Producto save(Producto p){
        if(p.getCategoria()==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Se requiere Categoria!");
        return repo.save(p);
    }

    public Producto update(Long id, Producto in){
        Producto db = get(id);
        if(in.getNombre()!=null) db.setNombre(in.getNombre());
        if(in.getPrecio()!=null) db.setPrecio(in.getPrecio());
        if(in.getCategoria()!=null) db.setCategoria(in.getCategoria());
        if(in.getActivo()!=null) db.setActivo(in.getActivo());
        return repo.save(db);
    }

    public void delete(Long id){
        if(!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El Producto no se encontro!");
        repo.deleteById(id);
    }

    public Producto assignCategoria(Long productoId, Long categoriaId){
        Producto p = get(productoId);
        Categoria c = catRepo.findById(categoriaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La Categoria no se encontro!"));
        p.setCategoria(c);
        return repo.save(p);
    }
}
