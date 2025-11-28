package com.primaria.app.DTO;

import java.util.List;

public class LibroDTO {
    private String id;
    private String titulo;
    private Integer anioPublicacion;
    private String editorial;

    private Integer totalCopias;       // NUEVO
    private Integer copiasDisponibles;

    private String categoriaId;
    private String estatus;

    private String sinopsis;
    private String imagenBase64;

    private List<String> autoresIds;

    public LibroDTO() {}

    public LibroDTO(String id, String titulo, Integer anioPublicacion, String editorial,
                    Integer totalCopias, Integer copiasDisponibles, String categoriaId,
                    String estatus, String sinopsis, String imagenBase64, List<String> autoresIds) {

        this.id = id;
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        this.editorial = editorial;

        this.totalCopias = totalCopias;
        this.copiasDisponibles = copiasDisponibles;

        this.categoriaId = categoriaId;
        this.estatus = estatus;
        this.sinopsis = sinopsis;
        this.imagenBase64 = imagenBase64;
        this.autoresIds = autoresIds;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Integer getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(Integer anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public Integer getTotalCopias() { return totalCopias; }
    public void setTotalCopias(Integer totalCopias) { this.totalCopias = totalCopias; }

    public Integer getCopiasDisponibles() { return copiasDisponibles; }
    public void setCopiasDisponibles(Integer copiasDisponibles) { this.copiasDisponibles = copiasDisponibles; }

    public String getCategoriaId() { return categoriaId; }
    public void setCategoriaId(String categoriaId) { this.categoriaId = categoriaId; }

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }

    public String getSinopsis() { return sinopsis; }
    public void setSinopsis(String sinopsis) { this.sinopsis = sinopsis; }

    public String getImagenBase64() { return imagenBase64; }
    public void setImagenBase64(String imagenBase64) { this.imagenBase64 = imagenBase64; }

    public List<String> getAutoresIds() { return autoresIds; }
    public void setAutoresIds(List<String> autoresIds) { this.autoresIds = autoresIds; }
}
