package com.marcell.pedido_ms.service;

import com.marcell.pedido_ms.client.ProductoClient;
import com.marcell.pedido_ms.client.dto.ProductoDto;
import com.marcell.pedido_ms.model.dto.PedidoDto;
import com.marcell.pedido_ms.model.dto.PedidoCreateRequestDto;

import com.marcell.pedido_ms.model.entity.DetallePedido;
import com.marcell.pedido_ms.model.entity.Pedido;
import com.marcell.pedido_ms.model.entity.User;
import com.marcell.pedido_ms.repository.PedidoRepository;
import com.marcell.pedido_ms.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepo;
    private final ProductoClient productoClient;
    private final UserRepository userRepo;

    public PedidoServiceImpl(PedidoRepository pedidoRepo, ProductoClient productoClient, UserRepository userRepo) {
        this.pedidoRepo = pedidoRepo; this.productoClient = productoClient; this.userRepo = userRepo;
    }

    @Override
    public PedidoDto crear(PedidoCreateRequestDto req) {
        User cliente = userRepo.findById(req.getClienteId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El Cliente no se encontro!"));
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);

        List<DetallePedido> detalles = new ArrayList<>();
        for (var it : req.getItems()) {
            ProductoDto prod = productoClient.getById(it.getProductoId());
            DetallePedido d = new DetallePedido();
            d.setPedido(pedido);
            d.setProductoId(prod.getId());
            d.setProductoNombre(prod.getNombre());
            d.setCantidad(it.getCantidad());
            d.setPrecioUnitario(prod.getPrecio());
            detalles.add(d);
        }
        pedido.setItems(detalles);
        Pedido saved = pedidoRepo.save(pedido);
        return toDto(saved);
    }

    @Override
    public PedidoDto get(Long id) {
        Pedido p = pedidoRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El Pedido no se encontro!"));
        return toDto(p);
    }

    @Override
    public List<PedidoDto> listByCliente(Long clienteId) {
        return pedidoRepo.findByCliente_Id(clienteId).stream().map(this::toDto).toList();
    }

    private PedidoDto toDto(Pedido p){
        PedidoDto dto = new PedidoDto();
        dto.setId(p.getId());
        dto.setClienteId(p.getCliente().getId());
        dto.setClienteNombre(p.getCliente().getName());
        dto.setFechaPedido(p.getFechaPedido());
        dto.setEstado(p.getEstado());
        List<Map<String,Object>> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (DetallePedido d : p.getItems()){
            Map<String,Object> line = new LinkedHashMap<>();
            line.put("productoId", d.getProductoId());
            line.put("productoNombre", d.getProductoNombre());
            line.put("cantidad", d.getCantidad());
            line.put("precioUnitario", d.getPrecioUnitario());
            BigDecimal subtotal = d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad()));
            line.put("subtotal", subtotal);
            items.add(line);
            total = total.add(subtotal);
        }
        dto.setItems(items);
        dto.setTotal(total);
        return dto;
    }
}
