package com.marcell.pedido_ms.client;

import com.marcell.pedido_ms.client.dto.ProductoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "producto-ms", url = "${producto.service.url}")
public interface ProductoClient {

    @GetMapping("/api/productos/{id}")
    ProductoDto getById(@PathVariable("id") Long id);
}

