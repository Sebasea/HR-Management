package com.hrManagement.modelo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class PerfilEmpleado {

@Id
@Column
private int codigo;
@Column
private String nombre;
@Column
private String habilidades;
@Column
private String experiencia;
@Column
private String certificaciones;

  public PerfilEmpleado(int codigo, String nombre, String habilidades, String experiencia, String certificaciones) {
    this.codigo = codigo;
    this.nombre = nombre;
    this.habilidades = habilidades;
    this.experiencia = experiencia;
    this.certificaciones = certificaciones;
  }

  public PerfilEmpleado() {

  }
}
