package com.minimarket;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    // Escenario 1: Intento de autenticación NO válido (Credenciales incorrectas)
    @Test
    public void dadoCredencialesInvalidas_cuandoLogin_entoncesFalla() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("usuarioInexistente")
                .password("claveEquivocada"))
                .andExpect(unauthenticated()) // Verifica que NO se inició sesión
                .andExpect(status().is3xxRedirection()); // Spring redirige de vuelta al login con error
    }

    // Escenario 2: Intento de acceso a ruta protegida sin estar autenticado
    @Test
    public void dadoUsuarioAnonimo_cuandoAccedeRutaProtegida_entoncesRedirigeAlLogin() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/productos"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login")); // Redirige al login
    }
}
