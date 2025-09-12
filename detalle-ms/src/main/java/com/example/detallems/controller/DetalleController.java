package com.example.detallems.controller;

import com.example.detallems.model.dto.DetalleCreateReq;
import com.example.detallems.model.dto.DetalleDto;
import com.example.detallems.service.DetalleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/detalles")
public class DetalleController {
    private final DetalleService service;
    public DetalleController(DetalleService s){ this.service = s; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DetalleDto crear(@RequestBody DetalleCreateReq req){ return service.crear(req); }

    @GetMapping
    public List<DetalleDto> list(@RequestParam Long pedidoId){ return service.listByPedido(pedidoId); }
}
