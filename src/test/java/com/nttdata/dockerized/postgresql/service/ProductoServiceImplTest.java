package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.exceptions.FieldValidationException;
import com.nttdata.dockerized.postgresql.exceptions.NotFoundException;
import com.nttdata.dockerized.postgresql.model.dto.ProductoDto;
import com.nttdata.dockerized.postgresql.model.entity.Categoria;
import com.nttdata.dockerized.postgresql.model.entity.Producto;
import com.nttdata.dockerized.postgresql.repository.CategoriaRepository;
import com.nttdata.dockerized.postgresql.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepo;

    @Mock
    private CategoriaRepository categoriaRepo;

    @InjectMocks
    private ProductoServiceImpl service;

    @BeforeEach
    void setup() {
    }

    @Test
    void list_ok_all() {
        when(productoRepo.findAll()).thenReturn(List.of(new Producto(), new Producto()));
        var out = service.list(null);
        assertEquals(2, out.size());
        verify(productoRepo).findAll();
        verifyNoMoreInteractions(productoRepo);
    }

    @Test
    void get_ok() {
        var p = new Producto();
        p.setId(1L);
        when(productoRepo.findById(1L)).thenReturn(Optional.of(p));
        var out = service.get(1L);
        assertNotNull(out);
        assertEquals(1L, out.getId());
        verify(productoRepo).findById(1L);
    }

    @Test
    void create_ok() {
        var dto = new ProductoDto();
        dto.setNombre("Mouse");
        dto.setPrecio(new BigDecimal("25.50"));
        dto.setCategoriaId(10L);

        var cat = new Categoria();
        cat.setId(10L);
        when(categoriaRepo.findById(10L)).thenReturn(Optional.of(cat));
        when(productoRepo.save(any())).thenAnswer(inv -> {
            Producto arg = inv.getArgument(0);
            arg.setId(100L);
            return arg;
        });

        var saved = service.create(dto);
        assertNotNull(saved.getId());
        assertEquals("Mouse", saved.getNombre());
        assertEquals(new BigDecimal("25.50"), saved.getPrecio());
        assertEquals(10L, saved.getCategoria().getId());
        assertEquals(Boolean.TRUE, saved.getActivo());
        verify(categoriaRepo).findById(10L);
        verify(productoRepo).save(any(Producto.class));
    }

    @Test
    void update_ok() {
        var db = new Producto();
        db.setId(1L);
        db.setNombre("Cascos");
        db.setPrecio(new BigDecimal("10.00"));
        db.setActivo(true);
        when(productoRepo.findById(1L)).thenReturn(Optional.of(db));
        when(productoRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var dto = new ProductoDto();
        dto.setNombre("CascosRTX");
        dto.setPrecio(new BigDecimal("20.00"));

        var updated = service.update(1L, dto);
        assertEquals("CascosRTX", updated.getNombre());
        assertEquals(new BigDecimal("20.00"), updated.getPrecio());
        assertTrue(updated.getActivo());
        verify(productoRepo).findById(1L);
        verify(productoRepo).save(any(Producto.class));
    }

    @Test
    void delete_ok() {
        when(productoRepo.existsById(5L)).thenReturn(true);
        assertDoesNotThrow(() -> service.delete(5L));
        verify(productoRepo).existsById(5L);
        verify(productoRepo).deleteById(5L);
    }

    @Test
    void get_notFound_throws() {
        when(productoRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.get(99L));
        verify(productoRepo).findById(99L);
    }

    @Test
    void create_validationError_throws() {
        var dto = new ProductoDto();
        assertThrows(FieldValidationException.class, () -> service.create(dto));
        verifyNoInteractions(productoRepo);
    }
}