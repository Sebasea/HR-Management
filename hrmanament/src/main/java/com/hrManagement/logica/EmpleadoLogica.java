package com.hrManagement.logica;

import com.hrManagement.controller.dto.EmpleadoDTO;
import com.hrManagement.modelo.Empleado;
import com.hrManagement.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoLogica {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoLogica(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public boolean guardarEmpleado(Empleado empleado) {
        try {
            empleadoRepository.save(empleado);
            return true;
        } catch (Exception e) {
            // Manejo de errores, puedes personalizarlo según tus necesidades
            e.printStackTrace();
            return false;
        }
    }

    public Empleado obtenerEmpleadoPorID(int id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    public List<Empleado> obtenerEmpleadosPorCargo(String rol, int edad) {
        return empleadoRepository.findByRolAndEdadLessThanEqual(rol, edad);
    }

    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }

    public boolean modificarEmpleado(int codigo, EmpleadoDTO empleadoActualizado) {
        if (empleadoRepository.existsById(codigo)) {
            Empleado empleado = empleadoRepository.findById(codigo).orElse(null);
            if (empleado != null) {
                // Actualiza los campos del empleado con los del DTO
                empleado.setNombre(empleadoActualizado.getNombre());
                empleado.setEdad(empleadoActualizado.getEdad());
                empleado.setRol(empleadoActualizado.getRol());
                empleado.setEmail(empleadoActualizado.getEmail());
                empleado.setNumeroTelefonico(empleadoActualizado.getNumeroTelefonico());

                guardarEmpleado(empleado); // Llama al método guardarEmpleado para guardar los cambios
                return true;
            }
        }
        return false;
    }

    public boolean eliminarEmpleado(int codigo) {
        if (empleadoRepository.existsById(codigo)) {
            try {
                empleadoRepository.deleteById(codigo);
                return true;
            } catch (Exception e) {
                // Manejo de errores, puedes personalizarlo según tus necesidades
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
