package com.hrManagement.controller;

import com.hrManagement.controller.dto.EmpleadoDTO;
import com.hrManagement.modelo.Empleado;
import com.hrManagement.logica.EmpleadoLogica;
import com.hrManagement.repository.EmpleadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleados") // Establecemos una ruta base para todos los endpoints
public class EmpleadoController {

    private final EmpleadoLogica empleadoLogica;

    private final EmpleadoRepository empleadoRepository;
    public EmpleadoController(EmpleadoLogica empleadoLogica, EmpleadoRepository empleadoRepository) {
        this.empleadoLogica = empleadoLogica;
        this.empleadoRepository = empleadoRepository;
    }

    @PostMapping("/agregar")
    public ResponseEntity<String> agregarEmpleado(@RequestBody EmpleadoDTO empleadoDTO) {
        if (empleadoRepository.findById(empleadoDTO.getCodigo()).isEmpty()) {
            Empleado empleado = new Empleado();
            empleado.setCodigo(empleadoDTO.getCodigo());
            empleado.setNombre(empleadoDTO.getNombre());
            empleado.setEdad(empleadoDTO.getEdad());
            empleado.setRol(empleadoDTO.getRol());
            empleado.setEmail(empleadoDTO.getEmail());
            empleado.setNumeroTelefonico(empleadoDTO.getNumeroTelefonico());
            empleadoRepository.save(empleado);
            boolean guardado = empleadoLogica.guardarEmpleado(empleado);

            if (guardado) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Empleado agregado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo agregar el empleado");
            }
        }else throw new IllegalArgumentException("Ya se encuentra registrado");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerDatosEmpleadoPorID(@PathVariable int codigo) {
        Empleado empleado = empleadoLogica.obtenerEmpleadoPorID(codigo);
        if (empleado != null) {
            return ResponseEntity.ok(empleado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Empleado> obtenerEmpleadosPorCargo(@RequestParam String rol, @RequestParam int edad) {
        return empleadoLogica.obtenerEmpleadosPorCargo(rol, edad);
    }

    @GetMapping("/todos")
    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoLogica.obtenerTodosLosEmpleados();
    }

    @PutMapping("/modificar/{codigo}")
    public ResponseEntity<String> modificarEmpleado(@PathVariable int codigo, @RequestBody EmpleadoDTO empleadoActualizado) {
        boolean modificado = empleadoLogica.modificarEmpleado(codigo, empleadoActualizado);
        if (modificado) {
            return ResponseEntity.ok("Empleado actualizado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado");
        }
    }

    @DeleteMapping("/eliminar/{codigo}")
    public ResponseEntity<String> eliminarEmpleado(@PathVariable int codigo) {
        boolean eliminado = empleadoLogica.eliminarEmpleado(codigo);
        if (eliminado) {
            return ResponseEntity.ok("Empleado eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado");
        }
    }
}