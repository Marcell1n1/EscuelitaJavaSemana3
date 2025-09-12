package com.marcell.pedido_ms.service;

import com.marcell.pedido_ms.model.dto.PedidoDto;
import com.marcell.pedido_ms.model.dto.PedidoCreateRequestDto;

import java.util.List;

public interface PedidoService {
    PedidoDto crear(PedidoCreateRequestDto req);
    PedidoDto get(Long id);
    List<PedidoDto> listByCliente(Long clienteId);
}