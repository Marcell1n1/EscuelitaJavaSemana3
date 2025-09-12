package com.marcell.pedido_ms.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "detalles_pedido")
public class DetallePedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="pedido_id")
    private Pedido pedido;
    @Column(name="producto_id")
    private Long productoId;
    @Column(name="producto_nombre")
    private String productoNombre;
    @Column
    private Integer cantidad;
    @Column
    private BigDecimal precioUnitario;
}