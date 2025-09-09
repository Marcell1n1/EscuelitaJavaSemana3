package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.dto.PedidoCreateRequestDto;
import com.nttdata.dockerized.postgresql.model.dto.PedidoDto;
import com.nttdata.dockerized.postgresql.model.entity.*;
import com.nttdata.dockerized.postgresql.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepo;
    private final ProductoRepository productoRepo;
    private final UserRepository userRepo;

    public PedidoServiceImpl(PedidoRepository pedidoRepo, ProductoRepository productoRepo, UserRepository userRepo) {
        this.pedidoRepo = pedidoRepo; this.productoRepo = productoRepo; this.userRepo = userRepo;
    }

    @Override
    public PedidoDto crear(PedidoCreateRequestDto req) {
        User cliente = userRepo.findById(req.getClienteId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El Cliente no se encontro!"));
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);

        List<DetallePedido> detalles = new ArrayList<>();
        for (var it : req.getItems()) {
            Producto p = productoRepo.findById(it.getProductoId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El Producto no se encontroo"));
            DetallePedido d = new DetallePedido();
            d.setPedido(pedido);
            d.setProducto(p);
            d.setCantidad(it.getCantidad());
            d.setPrecioUnitario(p.getPrecio());
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
            line.put("productoId", d.getProducto().getId());
            line.put("productoNombre", d.getProducto().getNombre());
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
