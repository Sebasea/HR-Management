package com.hrManagement.controller.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDTO {
    private int codigo;
    private String nombre;
    private int edad;
    private String rol;
    private String email;
    private int numeroTelefonico;
}