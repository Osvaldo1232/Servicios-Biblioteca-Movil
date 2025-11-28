package com.primaria.app.DTO;

import com.primaria.app.Model.EstatusPrestamo;
import java.time.LocalDate;

public class PrestamoDTO {

    private String id;
    private String usuarioId;     
    private String libroId;
    private Integer cantidad;
    private Integer cantidadDevuelta;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private EstatusPrestamo estatus;

    public PrestamoDTO() {}

    public PrestamoDTO(String id,
                       String usuarioId,
                       String libroId,
                       Integer cantidad,
                       Integer cantidadDevuelta,
                       LocalDate fechaPrestamo,
                       LocalDate fechaDevolucion,
                       EstatusPrestamo estatus) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.libroId = libroId;
        this.cantidad = cantidad;
        this.cantidadDevuelta = cantidadDevuelta;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estatus = estatus;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getLibroId() { return libroId; }
    public void setLibroId(String libroId) { this.libroId = libroId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Integer getCantidadDevuelta() { return cantidadDevuelta; }
    public void setCantidadDevuelta(Integer cantidadDevuelta) { this.cantidadDevuelta = cantidadDevuelta; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public EstatusPrestamo getEstatus() { return estatus; }
    public void setEstatus(EstatusPrestamo estatus) { this.estatus = estatus; }
}
