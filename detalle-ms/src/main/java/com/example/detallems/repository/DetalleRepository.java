package com.example.detallems.repository;


import com.example.detallems.model.entity.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleRepository extends JpaRepository<Detalle, Long> {
    List<Detalle> findByPedidoId(Long pedidoId);
}