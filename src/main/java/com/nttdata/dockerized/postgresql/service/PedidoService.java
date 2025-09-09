package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.dto.PedidoCreateRequestDto;
import com.nttdata.dockerized.postgresql.model.dto.PedidoDto;
import java.util.List;

public interface PedidoService {
    PedidoDto crear(PedidoCreateRequestDto req);
    PedidoDto get(Long id);
    List<PedidoDto> listByCliente(Long clienteId);
}