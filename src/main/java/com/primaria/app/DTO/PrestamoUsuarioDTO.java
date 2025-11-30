package com.primaria.app.DTO;

import java.time.LocalDate;
import java.util.List;

public class PrestamoUsuarioDTO {

    private String titulo;
    private byte[] imagen;
    private Integer cantidad;
    private String estatus;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private List<String> autores;

    public PrestamoUsuarioDTO() {}

    public PrestamoUsuarioDTO(String titulo, byte[] imagen, Integer cantidad, String estatus,
                              LocalDate fechaPrestamo, LocalDate fechaDevolucion, List<String> autores) {
        this.titulo = titulo;
        this.imagen = imagen;
        this.cantidad = cantidad;
        this.estatus = estatus;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.autores = autores;
    }

    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public List<String> getAutores() {
        return autores;
    }

    public void setAutores(List<String> autores) {
        this.autores = autores;
    }

}
