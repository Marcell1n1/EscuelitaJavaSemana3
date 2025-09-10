package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.exceptions.BusinessException;
import com.nttdata.dockerized.postgresql.exceptions.FieldValidationException;
import com.nttdata.dockerized.postgresql.exceptions.NotFoundException;
import com.nttdata.dockerized.postgresql.mapper.CatalogoMapper;
import com.nttdata.dockerized.postgresql.model.dto.ProductoDto;
import com.nttdata.dockerized.postgresql.model.entity.Categoria;
import com.nttdata.dockerized.postgresql.model.entity.Producto;
import com.nttdata.dockerized.postgresql.repository.CategoriaRepository;
import com.nttdata.dockerized.postgresql.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repo;
    private final CategoriaRepository catRepo;

    public ProductoServiceImpl(ProductoRepository repo, CategoriaRepository catRepo) {
        this.repo = repo; this.catRepo = catRepo;
    }

    @Override
    public List<Producto> list(Long categoriaId) {
        return (categoriaId != null) ? repo.findByCategoria_Id(categoriaId) : repo.findAll();
    }

    @Override
    public Producto get(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Producto no encontrado"));
    }

    @Override
    public Producto create(ProductoDto dto) {
        Map<String,String> errors = new LinkedHashMap<>();
        if (dto.getNombre()==null || dto.getNombre().isBlank()){
            errors.put("nombre","obligatorio");
        }
        if (dto.getPrecio()==null){
            errors.put("precio","obligatorio");
        } else if (dto.getPrecio().compareTo(BigDecimal.ZERO) <= 0){
            errors.put("precio","debe ser > 0");
        }
        if (dto.getCategoriaId()==null){
            errors.put("categoriaId","obligatorio");
        }
        if (!errors.isEmpty()){
            throw new FieldValidationException("Errores de validación", errors);
        }
        Categoria cat = catRepo.findById(dto.getCategoriaId()).orElseThrow(() -> new NotFoundException("Categoria no encontrada"));
        Producto p = CatalogoMapper.INSTANCE.toEntity(dto);
        p.setCategoria(cat);
        p.setActivo(Boolean.TRUE);
        return repo.save(p);
    }

    @Override
    public Producto update(Long id, ProductoDto dto) {
        Producto db = get(id);
        if (dto.getNombre()!=null) {
            db.setNombre(dto.getNombre());
        }
        if (dto.getPrecio()!=null) {
            if (dto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
                throw new FieldValidationException("Errores de validación", Map.of("precio", "debe ser > 0"));
            }
            db.setPrecio(dto.getPrecio());
        }
        if (dto.getCategoriaId()!=null) {
            Categoria cat = catRepo.findById(dto.getCategoriaId()).orElseThrow(() -> new NotFoundException("Categoria no encontrada"));
            db.setCategoria(cat);
        }
        if (dto.getActivo()!=null){
            db.setActivo(dto.getActivo());
        }
        return repo.save(db);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Producto no encontrado");
        repo.deleteById(id);
    }

    @Override
    public Producto assignCategoria(Long productoId, Long categoriaId) {
        Producto p = get(productoId);
        if (Boolean.FALSE.equals(p.getActivo())){
            throw new BusinessException("PRD_INACTIVE", "No se puede asignar categoría a un producto inactivo");
        }
        Categoria c = catRepo.findById(categoriaId).orElseThrow(() -> new NotFoundException("Categoria no encontrada"));
        p.setCategoria(c);
        return repo.save(p);
    }
}
