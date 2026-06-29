package com.minimarket;

import com.minimarket.entity.Rol;
import com.minimarket.entity.Usuario;
import com.minimarket.repository.UsuarioRepository;
import com.minimarket.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioUnitTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuarioPrueba;

    @BeforeEach
    void setUp() {
        usuarioPrueba = new Usuario();
        usuarioPrueba.setId(1L);
        usuarioPrueba.setUsername("cliente_test");
        usuarioPrueba.setPassword("12345");
        
        Set<Rol> roles = new HashSet<>();
        roles.add(new Rol("CLIENTE"));
        usuarioPrueba.setRoles(roles);
    }

    // Prueba de disponibilidad: Verifica que el usuario tenga datos obligatorios
    @Test
    public void dadoUsuarioCuandoValidarDatosObligatoriosEntoncesEstanCompletos() {
        when(usuarioRepository.findByUsername(anyString())).thenReturn(Optional.of(usuarioPrueba));

        Optional<Usuario> usuarioEncontrado = usuarioService.findByUsername("cliente_test");

        assertTrue(usuarioEncontrado.isPresent());
        assertNotNull(usuarioEncontrado.get().getUsername(), "El username no debe ser nulo");
        assertNotNull(usuarioEncontrado.get().getPassword(), "El password no debe ser nulo");
    }

    // Prueba de acceso según rol: Verifica la asignación de roles
    @Test
    public void dadoUsuarioCuandoVerificarRolEntoncesRolEsCorrecto() {
        boolean tieneRolCliente = usuarioPrueba.getRoles().stream()
                .anyMatch(rol -> rol.getNombre().equals("CLIENTE"));

        assertTrue(tieneRolCliente, "El usuario debe tener el rol CLIENTE");
    }
}