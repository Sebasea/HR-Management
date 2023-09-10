package com.hrManagement.logica;

import com.hrManagement.modelo.Empleado;
import com.hrManagement.modelo.PerfilEmpleado;
import com.hrManagement.repository.EmpleadoRepository;
import com.hrManagement.repository.PerfilEmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfilEmpleadoLogica {

    private final PerfilEmpleadoRepository perfilEmpleadoRepository;

    public PerfilEmpleadoLogica(PerfilEmpleadoRepository perfilEmpleadoRepository) {
        this.perfilEmpleadoRepository = perfilEmpleadoRepository;
    }

    public boolean guardarPerfilEmpleado(PerfilEmpleado perfilEmpleado) {
        try {
            perfilEmpleadoRepository.save(perfilEmpleado);
            return true;
        } catch (Exception e) {
            // Manejo de errores, puedes personalizarlo según tus necesidades
            e.printStackTrace();
            return false;
        }
    }

    public PerfilEmpleado obtenerPerfilEmpleadoPorID(int id) {
        return perfilEmpleadoRepository.findById(id).orElse(null);
    }

    public List<PerfilEmpleado> obtenerTodosLosPerfilesDeEmpleados() {
        return perfilEmpleadoRepository.findAll();
    }
}
