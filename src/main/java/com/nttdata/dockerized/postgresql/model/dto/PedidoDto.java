package com.nttdata.dockerized.postgresql.model.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PedidoDto {
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private LocalDateTime fechaPedido;
    private String estado;
    private BigDecimal total;
    private List<Map<String,Object>> items;
}