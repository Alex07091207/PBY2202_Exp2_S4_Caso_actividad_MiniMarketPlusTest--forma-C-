package com.minimarket;

import com.minimarket.entity.Inventario;
import com.minimarket.entity.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class InventarioUnitTest {

    private Inventario inventario;
    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(99L);
        producto.setNombre("Aceite");

        inventario = new Inventario();
        inventario.setId(1L);
        inventario.setProducto(producto);
        inventario.setCantidad(50);
        inventario.setTipoMovimiento("Entrada");
        inventario.setFechaMovimiento(new Date());
    }

    // Prueba de Información de Movimiento
    @Test
    public void dadoMovimientoInventarioCuandoValidarCamposEntoncesNoSonNulosNiVacios() {
        assertNotNull(inventario.getTipoMovimiento(), "El tipo de movimiento no puede ser nulo");
        assertFalse(inventario.getTipoMovimiento().trim().isEmpty(), "El tipo de movimiento no puede estar vacío");
        assertNotNull(inventario.getCantidad(), "La cantidad no puede ser nula");
        assertTrue(inventario.getCantidad() > 0, "La cantidad del movimiento debe ser mayor a cero");
    }

    // Prueba de Relación Producto-Inventario
    @Test
    public void dadoMovimientoInventarioCuandoValidarProductoEntoncesAsociacionEsCorrecta() {
        assertNotNull(inventario.getProducto(), "El inventario debe estar asociado a un producto obligatoriamente");
        assertEquals(99L, inventario.getProducto().getId(), "El ID del producto asociado debe ser el correcto");
        assertEquals("Aceite", inventario.getProducto().getNombre());
    }
}