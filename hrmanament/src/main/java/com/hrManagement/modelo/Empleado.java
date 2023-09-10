package com.hrManagement.modelo;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
public class Empleado {
    @Id
    @Column
    int codigo;
    @Column
    String nombre;
    @Column
    int edad;
    @Column
    String rol;
    @Column
    String email;
    @Column
    int numeroTelefonico;

    public Empleado(int codigo, String nombre, int edad, String rol, String email, int numeroTelefonico) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.edad = edad;
        this.rol = rol;
        this.email = email;
        this.numeroTelefonico = numeroTelefonico;
    }

    public Empleado() {

    }
}
