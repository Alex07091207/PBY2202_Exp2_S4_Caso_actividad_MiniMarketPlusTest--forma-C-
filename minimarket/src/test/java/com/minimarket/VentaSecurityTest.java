package com.minimarket;

import com.minimarket.entity.Venta;
import com.minimarket.service.VentaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VentaSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VentaService ventaService;

    // Escenario 1: Éxito - El Cajero puede registrar ventas
    @Test
    @WithMockUser(username = "cajeroUser", roles = {"CAJERO"})
    public void dadoCajeroCuandoRegistraVentaEntoncesOk() throws Exception {
        when(ventaService.save(any(Venta.class))).thenReturn(new Venta());

        String ventaJson = "{\"fecha\":\"2023-10-10\", \"usuario\": {\"id\": 1}}"; // JSON simplificado

        mockMvc.perform(post("/api/ventas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ventaJson))
                .andExpect(status().isOk());
    }

    // Escenario 2: Error - Un Cliente NO puede registrar ventas
    @Test
    @WithMockUser(username = "clienteUser", roles = {"CLIENTE"})
    public void dadoClienteCuandoRegistraVentaEntoncesProhibido() throws Exception {
        String ventaJson = "{\"fecha\":\"2023-10-10\", \"usuario\": {\"id\": 1}}";

        mockMvc.perform(post("/api/ventas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ventaJson))
                .andExpect(status().isForbidden()); // 403 Forbidden
    }
}