package com.hrManagement.controller;

import com.hrManagement.controller.dto.PerfilEmpleadoDTO;
import com.hrManagement.logica.PerfilEmpleadoLogica;
import com.hrManagement.modelo.Empleado;
import com.hrManagement.modelo.PerfilEmpleado;
import com.hrManagement.repository.EmpleadoRepository;
import com.hrManagement.repository.PerfilEmpleadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/perfilEmpleado") // Establecemos una ruta base para todos los endpoints
public class PerfilEmpleadoController {

    private final PerfilEmpleadoLogica perfilEmpleadoLogica;
    private final PerfilEmpleadoRepository perfilEmpleadoRepository;
    private final EmpleadoRepository empleadoRepository;

    public PerfilEmpleadoController(PerfilEmpleadoLogica perfilEmpleadoLogica, PerfilEmpleadoRepository perfilEmpleadoRepository, EmpleadoRepository empleadoRepository) {
        this.perfilEmpleadoLogica = perfilEmpleadoLogica;
        this.perfilEmpleadoRepository = perfilEmpleadoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    @PostMapping("/agregar")
    public ResponseEntity<String> agregarPerfilEmpleado(@RequestBody PerfilEmpleadoDTO perfilEmpleadoDTO) {
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(perfilEmpleadoDTO.getCodigo());

        if (empleadoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede agregar el perfil de empleado. El empleado no existe.");
        }

        PerfilEmpleado perfilEmpleado = new PerfilEmpleado();
        perfilEmpleado.setCodigo(perfilEmpleadoDTO.getCodigo());
        perfilEmpleado.setNombre(perfilEmpleadoDTO.getNombre());
        perfilEmpleado.setHabilidades(perfilEmpleadoDTO.getHabilidades());
        perfilEmpleado.setExperiencia(perfilEmpleadoDTO.getExperiencia());
        perfilEmpleado.setCertificaciones(perfilEmpleadoDTO.getCertificaciones());

        perfilEmpleadoRepository.save(perfilEmpleado);

        boolean guardado = perfilEmpleadoLogica.guardarPerfilEmpleado(perfilEmpleado);
        if (guardado) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Empleado agregado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo agregar el empleado");
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<PerfilEmpleado> obtenerDatosEmpleadoPorID(@PathVariable int id) {
        PerfilEmpleado perfilEmpleado = perfilEmpleadoLogica.obtenerPerfilEmpleadoPorID(id);
        if (perfilEmpleado != null) {
            return ResponseEntity.ok(perfilEmpleado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/todos")
    public List<PerfilEmpleado> obtenerTodosLosPerfilesDeEmpleados() {
        return perfilEmpleadoLogica.obtenerTodosLosPerfilesDeEmpleados();
    }

}
