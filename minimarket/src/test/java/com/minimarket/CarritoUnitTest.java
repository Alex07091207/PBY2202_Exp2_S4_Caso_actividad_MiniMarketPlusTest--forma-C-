package com.minimarket;

import com.minimarket.entity.Carrito;
import com.minimarket.entity.Producto;
import com.minimarket.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarritoUnitTest {

    private Carrito carrito;
    private Producto producto;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("cliente_frecuente");

        producto = new Producto();
        producto.setId(10L);
        producto.setNombre("Bebida");
        producto.setStock(10); // Stock disponible

        carrito = new Carrito();
        carrito.setId(1L);
        carrito.setUsuario(usuario);
        carrito.setProducto(producto);
        carrito.setCantidad(2);
    }

    // Prueba de disponibilidad de stock al agregar al carrito
    @Test
    public void dadoCarritoCuandoAgregarProductoEntoncesValidaStockSuficiente() {
        boolean puedeAgregar = carrito.getCantidad() <= carrito.getProducto().getStock();
        
        assertTrue(puedeAgregar, "El producto solo se puede agregar si hay stock suficiente en el inventario");
    }

    // Validación de relación Producto-Usuario
    @Test
    public void dadoCarritoCuandoVerificarRelacionEntoncesUsuarioEsCorrecto() {
        assertNotNull(carrito.getUsuario(), "El carrito debe estar asociado a un usuario");
        assertEquals("cliente_frecuente", carrito.getUsuario().getUsername(), "El usuario asociado debe coincidir");
        assertNotNull(carrito.getProducto(), "El carrito debe tener un producto asociado");
    }
}