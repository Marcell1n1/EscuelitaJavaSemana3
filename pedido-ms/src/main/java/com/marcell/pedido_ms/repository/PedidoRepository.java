package com.marcell.pedido_ms.repository;

import com.marcell.pedido_ms.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByCliente_Id(Long userId);
}