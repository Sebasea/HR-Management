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
    @Column
    boolean eliminar;

    public Empleado(int codigo, String nombre, int edad, String rol, String email, int numeroTelefonico, boolean eliminar) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.edad = edad;
        this.rol = rol;
        this.email = email;
        this.numeroTelefonico = numeroTelefonico;
        this.eliminar = eliminar;
    }

    public Empleado() {

    }
}
