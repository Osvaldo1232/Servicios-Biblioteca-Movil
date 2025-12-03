package com.primaria.app.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDate;

@Entity
@Table(name = "sanciones")
public class Sancion {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, updatable = false, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestamo_id", nullable = false)
    private Prestamo prestamo;

    @Column(nullable = false, length = 200)
    private String motivo;

    @Column(name = "fecha_sancion", nullable = false)
    private LocalDate fechaSancion = LocalDate.now();

    public Sancion() {}

    public Sancion(Prestamo prestamo, String motivo) {
        this.prestamo = prestamo;
        this.motivo = motivo;
        this.fechaSancion = LocalDate.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Prestamo getPrestamo() { return prestamo; }
    public void setPrestamo(Prestamo prestamo) { this.prestamo = prestamo; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public LocalDate getFechaSancion() { return fechaSancion; }
    public void setFechaSancion(LocalDate fechaSancion) { this.fechaSancion = fechaSancion; }
}
