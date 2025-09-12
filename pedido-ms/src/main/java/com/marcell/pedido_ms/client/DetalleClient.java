package com.marcell.pedido_ms.client;

import com.marcell.pedido_ms.client.dto.DetalleCreateReq;
import com.marcell.pedido_ms.client.dto.DetalleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name="detalle-ms", url="${detalle.service.url}")
public interface DetalleClient {

    @PostMapping("/api/detalles")
    DetalleDto crear(@RequestBody DetalleCreateReq req);

    @GetMapping("/api/detalles")
    List<DetalleDto> listByPedido(@RequestParam("pedidoId") Long pedidoId);
}
