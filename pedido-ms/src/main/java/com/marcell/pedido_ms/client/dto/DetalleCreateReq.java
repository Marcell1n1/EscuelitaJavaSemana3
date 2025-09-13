package com.marcell.pedido_ms.client.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
@Getter
@Setter
public class DetalleCreateReq {
    private Long pedidoId;
    private Long productoId;
    private String productoNombre;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}