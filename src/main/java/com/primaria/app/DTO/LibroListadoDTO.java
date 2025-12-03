package com.primaria.app.DTO;

import java.util.List;

public class LibroListadoDTO {

	private String id;
    private String titulo;
    private String sinopsis;
    private Integer copiasDisponibles;
    private String imagenBase64;
    private List<String> autores;

    public LibroListadoDTO(String id, String titulo, String sinopsis, Integer copiasDisponibles,
                           String imagenBase64, List<String> autores) {
    	this.id=id;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.copiasDisponibles = copiasDisponibles;
        this.imagenBase64 = imagenBase64;
        this.autores = autores;
    }

    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getSinopsis() { return sinopsis; }
    public void setSinopsis(String sinopsis) { this.sinopsis = sinopsis; }

    public Integer getCopiasDisponibles() { return copiasDisponibles; }
    public void setCopiasDisponibles(Integer copiasDisponibles) { this.copiasDisponibles = copiasDisponibles; }

    public String getImagenBase64() { return imagenBase64; }
    public void setImagenBase64(String imagenBase64) { this.imagenBase64 = imagenBase64; }

    public List<String> getAutores() { return autores; }
    public void setAutores(List<String> autores) { this.autores = autores; }
}
