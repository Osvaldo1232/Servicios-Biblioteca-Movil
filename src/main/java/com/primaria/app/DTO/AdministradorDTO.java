package com.primaria.app.DTO;

public class AdministradorDTO extends UsuarioDTO {

    private String departamento;

    private String telefono;
    

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}