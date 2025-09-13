package com.example.detallems.service;

import com.example.detallems.model.dto.DetalleCreateReq;
import com.example.detallems.model.dto.DetalleDto;
import com.example.detallems.model.entity.Detalle;
import com.example.detallems.repository.DetalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleServiceImpl implements DetalleService{
    private final DetalleRepository repo;
    public DetalleServiceImpl(DetalleRepository repo){ this.repo = repo; }

    public DetalleDto crear(DetalleCreateReq r){
        Detalle d = new Detalle();
        d.setPedidoId(r.getPedidoId());
        d.setProductoId(r.getProductoId());
        d.setProductoNombre(r.getProductoNombre());
        d.setCantidad(r.getCantidad());
        d.setPrecioUnitario(r.getPrecioUnitario());
        d = repo.save(d);
        DetalleDto dto = new DetalleDto();
        dto.setId(d.getId()); dto.setPedidoId(d.getPedidoId()); dto.setProductoId(d.getProductoId());
        dto.setProductoNombre(d.getProductoNombre()); dto.setCantidad(d.getCantidad()); dto.setPrecioUnitario(d.getPrecioUnitario());
        return dto;
    }
    public List<DetalleDto> listByPedido(Long pedidoId){
        return repo.findByPedidoId(pedidoId).stream().map(d -> {
            DetalleDto dto = new DetalleDto();
            dto.setId(d.getId()); dto.setPedidoId(d.getPedidoId()); dto.setProductoId(d.getProductoId());
            dto.setProductoNombre(d.getProductoNombre()); dto.setCantidad(d.getCantidad()); dto.setPrecioUnitario(d.getPrecioUnitario());
            return dto;
        }).toList();
    }
}
