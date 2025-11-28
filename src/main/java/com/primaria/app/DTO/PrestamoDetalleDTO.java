package com.primaria.app.DTO;

import com.primaria.app.Model.EstatusPrestamo;
import java.time.LocalDate;

public class PrestamoDetalleDTO {

    private String id;
    private String nombreUsuario;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String libroTitulo;
    private Integer cantidad;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private EstatusPrestamo estatus;

    public PrestamoDetalleDTO(String id, String nombreUsuario, String apellidoPaterno,
                              String apellidoMaterno, String libroTitulo, Integer cantidad,
                              LocalDate fechaPrestamo, LocalDate fechaDevolucion, EstatusPrestamo estatus) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.libroTitulo = libroTitulo;
        this.cantidad = cantidad;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estatus = estatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getLibroTitulo() {
        return libroTitulo;
    }

    public void setLibroTitulo(String libroTitulo) {
        this.libroTitulo = libroTitulo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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

    public EstatusPrestamo getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusPrestamo estatus) {
        this.estatus = estatus;
    }

}
