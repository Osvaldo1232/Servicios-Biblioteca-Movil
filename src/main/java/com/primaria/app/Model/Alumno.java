package com.primaria.app.Model;


import jakarta.persistence.Entity;


@Entity
public class Alumno extends Usuario {

    private String matricula;

   
   
    
    // Getters y Setters

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

   
}