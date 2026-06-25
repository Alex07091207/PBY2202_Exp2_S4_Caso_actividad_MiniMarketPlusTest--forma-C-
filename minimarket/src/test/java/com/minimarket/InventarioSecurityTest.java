package com.minimarket;

import com.minimarket.entity.Inventario;
import com.minimarket.service.InventarioService;
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
public class InventarioSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService inventarioService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void dadoAdminCuandoRegistraMovimientoEntoncesOk() throws Exception {
        when(inventarioService.save(any(Inventario.class))).thenReturn(new Inventario());

        String invJson = "{\"cantidad\":50, \"tipoMovimiento\":\"Entrada\"}";

        mockMvc.perform(post("/api/inventario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invJson))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "cajero", roles = {"CAJERO"})
    public void dadoCajeroCuandoRegistraMovimientoEntoncesProhibido() throws Exception {
        String invJson = "{\"cantidad\":50, \"tipoMovimiento\":\"Entrada\"}";

        mockMvc.perform(post("/api/inventario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invJson))
                .andExpect(status().isForbidden());
    }
}