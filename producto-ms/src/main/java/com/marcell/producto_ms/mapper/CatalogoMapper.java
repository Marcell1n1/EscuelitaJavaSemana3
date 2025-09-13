package com.marcell.producto_ms.mapper;

import com.marcell.producto_ms.model.dto.CategoriaDto;
import com.marcell.producto_ms.model.dto.ProductoDto;
import com.marcell.producto_ms.model.entity.Categoria;
import com.marcell.producto_ms.model.entity.Producto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface CatalogoMapper {
    CatalogoMapper INSTANCE = Mappers.getMapper(CatalogoMapper.class);
    CategoriaDto toDto(Categoria c);
    List<CategoriaDto> toDtoCategorias(List<Categoria> cs);
    Categoria toEntity(CategoriaDto dto);

    @Mappings({
            @Mapping(target="categoriaId", source="categoria.id"),
            @Mapping(target="categoriaNombre", source="categoria.nombre")
    })
    ProductoDto toDto(Producto p);
    List<ProductoDto> toDtoProductos(List<Producto> ps);

    @Mapping(target="categoria", ignore = true)
    Producto toEntity(ProductoDto dto);
}