package com.minimarket;

import com.minimarket.entity.DetalleVenta;
import com.minimarket.entity.Producto;
import com.minimarket.entity.Usuario;
import com.minimarket.entity.Venta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VentaUnitTest {

    private Venta venta;
    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setNombre("Galletas");
        producto.setPrecio(1500.0);
        producto.setStock(50); // Stock inicial

        Usuario usuario = new Usuario();
        usuario.setUsername("cajero_test");

        venta = new Venta();
        venta.setId(1L);
        venta.setUsuario(usuario);
        venta.setFecha(new Date());

        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setCantidad(5);
        detalle.setPrecio(producto.getPrecio());
        detalle.setVenta(venta);

        List<DetalleVenta> detalles = new ArrayList<>();
        detalles.add(detalle);
        venta.setDetalles(detalles);
    }

    // Validación de stock de productos
    @Test
    public void dadaVentaCuandoValidarStockEntoncesHayStockSuficiente() {
        DetalleVenta detalle = venta.getDetalles().get(0);
        
        boolean hayStock = detalle.getProducto().getStock() >= detalle.getCantidad();
        
        assertTrue(hayStock, "Debe haber stock suficiente para realizar la venta");
    }

    // Cálculo correcto de total
    @Test
    public void dadaVentaCuandoCalcularTotalEntoncesSumaEsCorrecta() {
        double totalEsperado = 0;
        for (DetalleVenta detalle : venta.getDetalles()) {
            totalEsperado += (detalle.getPrecio() * detalle.getCantidad());
        }

        assertEquals(7500.0, totalEsperado, "El cálculo del total de la venta debe ser exacto");
    }
}