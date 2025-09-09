package com.nttdata.dockerized.postgresql.controller;

import com.nttdata.dockerized.postgresql.model.dto.PedidoCreateRequestDto;
import com.nttdata.dockerized.postgresql.model.dto.PedidoDto;
import com.nttdata.dockerized.postgresql.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService service;

    public PedidoController(PedidoService s){
        this.service=s;
    }

    @PostMapping
    public ResponseEntity<PedidoDto> crear(@RequestBody PedidoCreateRequestDto req){
        return ResponseEntity.ok(service.crear(req));
    }

    @GetMapping("/{id}") public ResponseEntity<PedidoDto> get(@PathVariable Long id){
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoDto>> listByCliente(@PathVariable Long clienteId){
        return ResponseEntity.ok(service.listByCliente(clienteId));
    }
}