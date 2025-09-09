package com.nttdata.dockerized.postgresql.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "productos")
public class Producto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private BigDecimal precio;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="categoria_id")
    private Categoria categoria;

    @Column
    private Boolean activo = Boolean.TRUE;
}