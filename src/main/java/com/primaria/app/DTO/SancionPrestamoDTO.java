package com.primaria.app.DTO;


import java.time.LocalDate;

public class SancionPrestamoDTO {
    private String motivo;
    private String nombreCompleto;
    private LocalDate fechaSancion;

    public SancionPrestamoDTO(String motivo, String nombreCompleto, LocalDate fechaSancion) {
        this.motivo = motivo;
        this.nombreCompleto = nombreCompleto;
        this.fechaSancion = fechaSancion;
    }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public LocalDate getFechaSancion() { return fechaSancion; }
    public void setFechaSancion(LocalDate fechaSancion) { this.fechaSancion = fechaSancion; }
}
