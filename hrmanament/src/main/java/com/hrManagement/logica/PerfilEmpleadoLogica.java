package com.hrManagement.logica;

import com.hrManagement.controller.dto.PerfilEmpleadoDTO;
import com.hrManagement.modelo.Empleado;
import com.hrManagement.modelo.PerfilEmpleado;
import com.hrManagement.repository.EmpleadoRepository;
import com.hrManagement.repository.PerfilEmpleadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerfilEmpleadoLogica {

    private final PerfilEmpleadoRepository perfilEmpleadoRepository;
    private final EmpleadoRepository empleadoRepository;
    public PerfilEmpleadoLogica(PerfilEmpleadoRepository perfilEmpleadoRepository, EmpleadoRepository empleadoRepository) {
        this.perfilEmpleadoRepository = perfilEmpleadoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    public boolean guardarPerfilEmpleado(PerfilEmpleadoDTO perfilEmpleadoDTO) {
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(perfilEmpleadoDTO.getCodigo());
        if (empleadoOptional.isEmpty()) {
            throw new EmpleadoNoExisteException("No se puede agregar el perfil de empleado. El empleado no existe.");
        }
        PerfilEmpleado perfilEmpleado = new PerfilEmpleado();
        perfilEmpleado.setCodigo(perfilEmpleadoDTO.getCodigo());
        perfilEmpleado.setNombre(perfilEmpleadoDTO.getNombre());
        perfilEmpleado.setHabilidades(perfilEmpleadoDTO.getHabilidades());
        perfilEmpleado.setExperiencia(perfilEmpleadoDTO.getExperiencia());
        perfilEmpleado.setCertificaciones(perfilEmpleadoDTO.getCertificaciones());

        try {
            perfilEmpleadoRepository.save(perfilEmpleado);
            return true;
        } catch (Exception e) {
            // Manejo de errores, puedes personalizarlo según tus necesidades
            e.printStackTrace();
            return false;
        }
    }
    public class EmpleadoNoExisteException extends RuntimeException {
        public EmpleadoNoExisteException(String message) {
            super(message);
        }
    }
    public PerfilEmpleado obtenerPerfilEmpleadoPorID(int id) {
        return perfilEmpleadoRepository.findById(id).orElse(null);
    }

    public List<PerfilEmpleado> obtenerTodosLosPerfilesDeEmpleados() {
        return perfilEmpleadoRepository.findAll();
    }
    public boolean eliminarPerfilEmpleado(int codigo) {
        Optional<PerfilEmpleado> perfilEmpleadoOptional = perfilEmpleadoRepository.findById(codigo);

        if (perfilEmpleadoOptional.isPresent()) {
            PerfilEmpleado perfilEmpleado = perfilEmpleadoOptional.get();
            perfilEmpleado.setEliminar(false); // Cambia el valor de la columna "eliminar" a false

            try {
                perfilEmpleadoRepository.save(perfilEmpleado);
                return true;
            } catch (Exception e) {
                // Manejo de errores, puedes personalizarlo según tus necesidades
                e.printStackTrace();
                return false;
            }
        }

        return false; // No se encontró el perfil del empleado con el código dado
    }
}
