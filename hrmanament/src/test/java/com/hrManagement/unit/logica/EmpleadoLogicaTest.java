package com.hrManagement.unit.logica;

import com.hrManagement.controller.dto.EmpleadoDTO;
import com.hrManagement.logica.EmpleadoLogica;
import com.hrManagement.modelo.Empleado;
import com.hrManagement.repository.EmpleadoRepository;
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

import static com.hrManagement.logica.RolEnum.ADMINISTRADOR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@ActiveProfiles(profiles = "test")
class EmpleadoLogicaTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    private EmpleadoLogica empleadoLogica;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        empleadoLogica = new EmpleadoLogica(empleadoRepository);
    }

    @Test
    public void testGuardarEmpleado() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setCodigo(1);
        empleadoDTO.setNombre("John Doe");
        empleadoDTO.setEdad(30);
        empleadoDTO.setRol(ADMINISTRADOR);
        empleadoDTO.setEmail("johndoe@example.com");
        empleadoDTO.setNumeroTelefonico(1234567890);
        empleadoDTO.setResponsabilidades("Administrar el sistema");

        when(empleadoRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(new Empleado());

        boolean result = empleadoLogica.guardarEmpleado(empleadoDTO);

        assertTrue(result);
        verify(empleadoRepository, times(1)).findById(1);
        verify(empleadoRepository, times(1)).save(any(Empleado.class));
    }

    @Test
    public void testGuardarEmpleadoExistente() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setCodigo(1);
        empleadoDTO.setNombre("John Doe");
        empleadoDTO.setEdad(30);
        empleadoDTO.setRol(ADMINISTRADOR);
        empleadoDTO.setEmail("johndoe@example.com");
        empleadoDTO.setNumeroTelefonico(1234567890);
        empleadoDTO.setResponsabilidades("Administrar el sistema");

        when(empleadoRepository.findById(anyInt())).thenReturn(Optional.of(new Empleado()));

        boolean result = empleadoLogica.guardarEmpleado(empleadoDTO);

        assertFalse(result);
        verify(empleadoRepository, times(1)).findById(1);
        verify(empleadoRepository, never()).save(any(Empleado.class));
    }

    @Test
    public void testObtenerEmpleadoPorID() {
        Empleado empleado = new Empleado();
        when(empleadoRepository.findById(anyInt())).thenReturn(Optional.of(empleado));

        Empleado result = empleadoLogica.obtenerEmpleadoPorID(1);

        assertEquals(empleado, result);
        verify(empleadoRepository, times(1)).findById(1);
    }

    @Test
    public void testObtenerEmpleadosPorCargo() {
        List<Empleado> empleados = new ArrayList<>();
        when(empleadoRepository.findByRol(any())).thenReturn(empleados);

        List<Empleado> result = empleadoLogica.obtenerEmpleadosPorCargo(ADMINISTRADOR);

        assertEquals(empleados, result);
        verify(empleadoRepository, times(1)).findByRol(ADMINISTRADOR);
    }

    @Test
    public void testObtenerTodosLosEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        when(empleadoRepository.findAll()).thenReturn(empleados);

        List<Empleado> result = empleadoLogica.obtenerTodosLosEmpleados();

        assertEquals(empleados, result);
        verify(empleadoRepository, times(1)).findAll();
    }

    @Test
    public void testModificarEmpleado() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setNombre("John Doe");
        empleadoDTO.setEdad(30);
        empleadoDTO.setRol(ADMINISTRADOR);
        empleadoDTO.setEmail("johndoe@example.com");
        empleadoDTO.setNumeroTelefonico(1234567890);
        empleadoDTO.setResponsabilidades("Administrar el sistema");

        Empleado empleado = new Empleado();
        empleado.setCodigo(1);
        empleado.setNombre("Jane Doe");
        empleado.setEdad(25);
        empleado.setRol(ADMINISTRADOR);
        empleado.setEmail("janedoe@example.com");
        empleado.setNumeroTelefonico(1234567891);
        empleado.setResponsabilidades("Usar el sistema");

        when(empleadoRepository.existsById(anyInt())).thenReturn(true);
        when(empleadoRepository.findById(anyInt())).thenReturn(Optional.of(empleado));
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(new Empleado());

        boolean result = empleadoLogica.modificarEmpleado(1, empleadoDTO);

        assertTrue(result);
        assertEquals(empleadoDTO.getNombre(), empleado.getNombre());
        assertEquals(empleadoDTO.getEdad(), empleado.getEdad());
        assertEquals(empleadoDTO.getRol(), empleado.getRol());
        assertEquals(empleadoDTO.getEmail(), empleado.getEmail());
        assertEquals(empleadoDTO.getNumeroTelefonico(), empleado.getNumeroTelefonico());
        assertEquals(empleadoDTO.getResponsabilidades(), empleado.getResponsabilidades());
        verify(empleadoRepository, times(1)).existsById(1);
        verify(empleadoRepository, times(1)).findById(1);
        verify(empleadoRepository, times(1)).save(any(Empleado.class));
    }

    @Test
    public void testModificarEmpleadoNoExistente() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setNombre("John Doe");
        empleadoDTO.setEdad(30);
        empleadoDTO.setRol(ADMINISTRADOR);
        empleadoDTO.setEmail("johndoe@example.com");
        empleadoDTO.setNumeroTelefonico(1234567890);
        empleadoDTO.setResponsabilidades("Administrar el sistema");

        when(empleadoRepository.existsById(anyInt())).thenReturn(false);

        boolean result = empleadoLogica.modificarEmpleado(1, empleadoDTO);

        assertFalse(result);
        verify(empleadoRepository, times(1)).existsById(1);
        verify(empleadoRepository, never()).findById(anyInt());
        verify(empleadoRepository, never()).save(any(Empleado.class));
    }

    @Test
    public void testEliminarEmpleado() {
        Empleado empleado = new Empleado();
        empleado.setCodigo(1);
        empleado.setEliminar(false);

        when(empleadoRepository.findById(anyInt())).thenReturn(Optional.of(empleado));
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(new Empleado());

        boolean result = empleadoLogica.eliminarEmpleado(1);

        assertTrue(result);
        assertTrue(empleado.isEliminar());
        verify(empleadoRepository, times(1)).findById(1);
        verify(empleadoRepository, times(1)).save(any(Empleado.class));
    }

    @Test
    public void testEliminarEmpleadoNoExistente() {
        when(empleadoRepository.findById(anyInt())).thenReturn(Optional.empty());

        boolean result = empleadoLogica.eliminarEmpleado(1);

        assertFalse(result);
        verify(empleadoRepository, times(1)).findById(1);
        verify(empleadoRepository, never()).save(any(Empleado.class));
    }
}