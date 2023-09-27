package com.hrManagement.unit.logica;

import com.hrManagement.controller.dto.PerfilEmpleadoDTO;
import com.hrManagement.logica.PerfilEmpleadoLogica;
import com.hrManagement.modelo.Empleado;
import com.hrManagement.modelo.PerfilEmpleado;
import com.hrManagement.repository.EmpleadoRepository;
import com.hrManagement.repository.PerfilEmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@ActiveProfiles(profiles = "test")
class PerfilEmpleadoLogicaTest {

    private PerfilEmpleadoLogica perfilEmpleadoLogica;

    @Mock
    private PerfilEmpleadoRepository perfilEmpleadoRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        perfilEmpleadoLogica = new PerfilEmpleadoLogica(perfilEmpleadoRepository, empleadoRepository);
    }

    @Test
    void guardarPerfilEmpleado_camposVacios() {
        PerfilEmpleadoDTO perfilEmpleadoDTO = new PerfilEmpleadoDTO();
        assertThrows(PerfilEmpleadoLogica.CamposVaciosException.class, () -> perfilEmpleadoLogica.guardarPerfilEmpleado(perfilEmpleadoDTO));
    }
/*
    @Test
    void guardarPerfilEmpleado_empleadoNoExiste() {
        PerfilEmpleadoDTO perfilEmpleadoDTO = new PerfilEmpleadoDTO();
        perfilEmpleadoDTO.setCodigo(1);
        when(empleadoRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(PerfilEmpleadoLogica.EmpleadoNoExisteException.class, () -> perfilEmpleadoLogica.guardarPerfilEmpleado(perfilEmpleadoDTO));
    }
*/
    @Test
    void guardarPerfilEmpleado_guardadoExitoso() {
        PerfilEmpleadoDTO perfilEmpleadoDTO = new PerfilEmpleadoDTO();
        perfilEmpleadoDTO.setCodigo(1);
        perfilEmpleadoDTO.setNombre("Nombre");
        perfilEmpleadoDTO.setHabilidades("Habilidades");
        perfilEmpleadoDTO.setExperiencia("Experiencia");
        perfilEmpleadoDTO.setCertificaciones("Certificaciones");
        Empleado empleado = new Empleado();
        empleado.setCodigo(1);
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));
        when(perfilEmpleadoRepository.save(any(PerfilEmpleado.class))).thenReturn(new PerfilEmpleado());
        assertTrue(perfilEmpleadoLogica.guardarPerfilEmpleado(perfilEmpleadoDTO));
    }

    @Test
    void obtenerPerfilEmpleadoPorID() {
        PerfilEmpleado perfilEmpleado = new PerfilEmpleado();
        perfilEmpleado.setCodigo(1);
        when(perfilEmpleadoRepository.findById(1)).thenReturn(Optional.of(perfilEmpleado));
        assertEquals(perfilEmpleado, perfilEmpleadoLogica.obtenerPerfilEmpleadoPorID(1));
    }

    @Test
    void obtenerTodosLosPerfilesDeEmpleados() {
        List<PerfilEmpleado> perfilEmpleadoList = new ArrayList<>();
        perfilEmpleadoList.add(new PerfilEmpleado());
        when(perfilEmpleadoRepository.findAll()).thenReturn(perfilEmpleadoList);
        assertEquals(perfilEmpleadoList, perfilEmpleadoLogica.obtenerTodosLosPerfilesDeEmpleados());
    }

    @Test
    void eliminarPerfilEmpleado_perfilEmpleadoNoExiste() {
        when(perfilEmpleadoRepository.findById(1)).thenReturn(Optional.empty());
        assertFalse(perfilEmpleadoLogica.eliminarPerfilEmpleado(1));
    }

    @Test
    void eliminarPerfilEmpleado_eliminacionExitosa() {
        PerfilEmpleado perfilEmpleado = new PerfilEmpleado();
        perfilEmpleado.setCodigo(1);
        when(perfilEmpleadoRepository.findById(1)).thenReturn(Optional.of(perfilEmpleado));
        when(perfilEmpleadoRepository.save(any(PerfilEmpleado.class))).thenReturn(new PerfilEmpleado());
        assertTrue(perfilEmpleadoLogica.eliminarPerfilEmpleado(1));
        assertTrue(perfilEmpleado.isEliminar());
    }
}