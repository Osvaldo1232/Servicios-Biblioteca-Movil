package com.primaria.app.DTO;

import com.primaria.app.Model.Estatus;


public class EmpleadoDTO extends UsuarioDTO {

   
    private String telefono;
    private String clavePresupuestal;
   
   
 
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

  

    public String getClavePresupuestal() {
        return clavePresupuestal;
    }

    public void setClavePresupuestal(String clavePresupuestal) {
        this.clavePresupuestal = clavePresupuestal;
    }

}
