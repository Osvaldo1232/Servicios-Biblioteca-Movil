package com.primaria.app.DTO;

import java.util.List;

public class LibroCategoriaDTO {

    private String id;
    private String titulo;
    private List<String> autores; 
    private List<String> autoresIds;  
    private Integer anioPublicacion;
    private String editorial;
    private Integer totalCopias;
    private Integer copiasDisponibles;
    private String categoriaId;
    private String categoriaNombre;
    private String estatus;
    
    private String imagen;    
    private String sinopsis; 

    public LibroCategoriaDTO() {}

    public LibroCategoriaDTO(
            String id, String titulo,
            List<String> autores,
            List<String> autoresIds,
            Integer anioPublicacion,
            String editorial,
            Integer totalCopias,
            Integer copiasDisponibles,
            String categoriaId,
            String categoriaNombre,
            String estatus,
            String imagen,     // <-- NUEVO
            String sinopsis    // <-- NUEVO
    ) {
        this.id = id;
        this.titulo = titulo;
        this.autores = autores;
        this.autoresIds = autoresIds;
        this.anioPublicacion = anioPublicacion;
        this.editorial = editorial;
        this.totalCopias = totalCopias;
        this.copiasDisponibles = copiasDisponibles;
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
        this.estatus = estatus;
        this.imagen = imagen;
        this.sinopsis = sinopsis;
    }

    // Getters y setters
    
    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public String getSinopsis() { return sinopsis; }
    public void setSinopsis(String sinopsis) { this.sinopsis = sinopsis; }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public List<String> getAutores() { return autores; }
    public void setAutores(List<String> autores) { this.autores = autores; }

    public List<String> getAutoresIds() { return autoresIds; }
    public void setAutoresIds(List<String> autoresIds) { this.autoresIds = autoresIds; }

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

    public String getCategoriaNombre() { return categoriaNombre; }
    public void setCategoriaNombre(String categoriaNombre) { this.categoriaNombre = categoriaNombre; }

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
}
