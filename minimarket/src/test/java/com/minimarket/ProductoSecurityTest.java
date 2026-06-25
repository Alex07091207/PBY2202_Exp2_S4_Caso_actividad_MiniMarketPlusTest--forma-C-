package com.minimarket;

import com.minimarket.entity.Producto;
import com.minimarket.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService; // Simulamos el servicio para no afectar la BD real

    // Escenario 1: Éxito - Usuario con rol ADMIN intenta modificar
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void dadoUsuarioAdminCuandoModificaProductoEntoncesOk() throws Exception {
        // Simulamos que el producto existe en el servicio
        when(productoService.findById(anyLong())).thenReturn(new Producto());
        when(productoService.save(any(Producto.class))).thenReturn(new Producto());

        String productoJson = "{\"nombre\":\"Producto Modificado\", \"precio\":1500.0, \"stock\":20}";

        mockMvc.perform(put("/api/productos/1") // La ruta PUT para actualizar un producto
                .contentType(MediaType.APPLICATION_JSON)
                .content(productoJson))
                .andExpect(status().isOk()); // Esperamos un 200 OK
    }

    // Escenario 2: Error - Usuario con rol CLIENTE o CAJERO intenta modificar
    @Test
    @WithMockUser(username = "cajero", roles = {"CAJERO"})
    public void dadoUsuarioCajeroCuandoModificaProductoEntoncesProhibido() throws Exception {
        String productoJson = "{\"nombre\":\"Producto Hackeado\", \"precio\":10.0, \"stock\":999}";

        mockMvc.perform(put("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productoJson))
                .andExpect(status().isForbidden()); // Esperamos un 403 Forbidden
    }

    // Escenario 3: Error - Usuario NO autenticado intenta modificar
    @Test
    public void dadoUsuarioAnonimoCuandoModificaProductoEntoncesNoAutorizado() throws Exception {
        String productoJson = "{\"nombre\":\"Producto Hackeado\", \"precio\":10.0, \"stock\":999}";

        mockMvc.perform(put("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productoJson))
                // Dependiendo de tu configuración exacta, Spring Security puede devolver 401 Unauthorized o 302 (Redirección al login)
                .andExpect(status().is3xxRedirection()); 
    }
}