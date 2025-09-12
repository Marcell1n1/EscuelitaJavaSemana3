package com.example.detallems.service;

import com.example.detallems.model.dto.DetalleCreateReq;
import com.example.detallems.model.dto.DetalleDto;

import java.util.List;

public interface DetalleService {
    public DetalleDto crear(DetalleCreateReq r);
    public List<DetalleDto> listByPedido(Long pedidoId);
}
