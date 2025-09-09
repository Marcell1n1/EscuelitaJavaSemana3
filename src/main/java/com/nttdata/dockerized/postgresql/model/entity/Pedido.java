package com.nttdata.dockerized.postgresql.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList; import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="cliente_id")   // User como “cliente”
    private User cliente;

    @Column
    private LocalDateTime fechaPedido = LocalDateTime.now();

    @Column
    private String estado = "CREADO";

    @OneToMany(mappedBy="pedido", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<DetallePedido> items = new ArrayList<>();
}