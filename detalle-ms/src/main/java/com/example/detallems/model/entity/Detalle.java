package com.example.detallems.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Entity
@Table(name="detalles_pedido")
public class Detalle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private Long pedidoId;
    @Column(nullable=false)
    private Long productoId;
    @Column(nullable=false)
    private String productoNombre;
    @Column(nullable=false)
    private Integer cantidad;
    @Column(nullable=false, precision=16, scale=2)
    private BigDecimal precioUnitario;

}
