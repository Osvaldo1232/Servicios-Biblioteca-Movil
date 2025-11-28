package com.primaria.app.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, updatable = false, nullable = false)
    private String id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;

    @Column(length = 100)
    private String editorial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    // 👇 NUEVO: total de copias registradas
    @Column(name = "total_copias", nullable = false)
    private Integer totalCopias;

    // 👇 Cantidad disponible para préstamo/apartado
    @Column(name = "copias_disponibles", nullable = false)
    private Integer copiasDisponibles;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estatus estatus = Estatus.ACTIVO;

    @ManyToMany
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autores> autores = new HashSet<>();

    // 👇 Nuevo: sinopsis del libro
    @Column(columnDefinition = "TEXT")
    private String sinopsis;

    // 👇 Nuevo: imagen guardada como BLOB
    @Lob
    @Column(name = "imagen", columnDefinition = "LONGBLOB")
    private byte[] imagen;

    public Libro() {}

    public Libro(String titulo, Integer anioPublicacion, String editorial,
                 Categoria categoria, Integer totalCopias, Integer copiasDisponibles,
                 Estatus estatus, String sinopsis, byte[] imagen) {

        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        this.editorial = editorial;
        this.categoria = categoria;

        this.totalCopias = totalCopias;
        this.copiasDisponibles = copiasDisponibles;

        this.estatus = estatus != null ? estatus : Estatus.ACTIVO;
        this.sinopsis = sinopsis;
        this.imagen = imagen;
    }

    // =============================
    // Getters y Setters
    // =============================

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Integer getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(Integer anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Integer getTotalCopias() { return totalCopias; }
    public void setTotalCopias(Integer totalCopias) { this.totalCopias = totalCopias; }

    public Integer getCopiasDisponibles() { return copiasDisponibles; }
    public void setCopiasDisponibles(Integer copiasDisponibles) { this.copiasDisponibles = copiasDisponibles; }

    public Estatus getEstatus() { return estatus; }
    public void setEstatus(Estatus estatus) { this.estatus = estatus; }

    public Set<Autores> getAutores() { return autores; }
    public void setAutores(Set<Autores> autores) { this.autores = autores; }

    public String getSinopsis() { return sinopsis; }
    public void setSinopsis(String sinopsis) { this.sinopsis = sinopsis; }

    public byte[] getImagen() { return imagen; }
    public void setImagen(byte[] imagen) { this.imagen = imagen; }
}
