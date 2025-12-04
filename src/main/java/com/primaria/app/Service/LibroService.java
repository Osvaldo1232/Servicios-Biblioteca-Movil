package com.primaria.app.Service;

import com.primaria.app.DTO.LibroActivoDTO;
import com.primaria.app.DTO.LibroCategoriaDTO;
import com.primaria.app.DTO.LibroDTO;
import com.primaria.app.DTO.LibroListadoDTO;
import com.primaria.app.Model.Autores;
import com.primaria.app.Model.Categoria;
import com.primaria.app.Model.Estatus;
import com.primaria.app.Model.Libro;
import com.primaria.app.repository.AutorRepository;
import com.primaria.app.repository.CategoriaRepository;
import com.primaria.app.repository.LibroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class LibroService {

    private final LibroRepository libroRepository;
    private final CategoriaRepository categoriaRepository;
    private final AutorRepository autorRepository;
    private final ModelMapper modelMapper;

    public LibroService(LibroRepository libroRepository,
                        CategoriaRepository categoriaRepository,
                        AutorRepository autorRepository,
                        ModelMapper modelMapper) {
        this.libroRepository = libroRepository;
        this.categoriaRepository = categoriaRepository;
        this.autorRepository = autorRepository;
        this.modelMapper = modelMapper;
    }



public List<LibroCategoriaDTO> listarTodos() {
    return libroRepository.findAll()
            .stream()
            .map(libro -> new LibroCategoriaDTO(
                    libro.getId(),
                    libro.getTitulo(),

                    libro.getAutores().stream()
                            .map(a -> a.getNombre() + " " + a.getApellidoPaterno())
                            .collect(Collectors.toList()),

                    libro.getAutores().stream()
                            .map(a -> a.getId())
                            .collect(Collectors.toList()),

                    libro.getAnioPublicacion(),
                    libro.getEditorial(),
                    libro.getTotalCopias(),
                    libro.getCopiasDisponibles(),
                    libro.getCategoria().getId(),
                    libro.getCategoria().getNombre(),
                    libro.getEstatus().name(),

                    libro.getImagen() != null
                            ? Base64.getEncoder().encodeToString(libro.getImagen())   // <-- CONVERSIÓN
                            : null,

                    libro.getSinopsis()
            ))
            .collect(Collectors.toList());
}

    public Optional<LibroDTO> obtenerPorId(String id) {
        return libroRepository.findById(id)
                .map(this::convertirADTO);
    }

    public LibroDTO crear(LibroDTO dto) {

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Libro libro = new Libro();
        libro.setTitulo(dto.getTitulo());
        libro.setAnioPublicacion(dto.getAnioPublicacion());
        libro.setEditorial(dto.getEditorial());

        libro.setTotalCopias(dto.getTotalCopias());
        libro.setCopiasDisponibles(dto.getCopiasDisponibles());

        libro.setCategoria(categoria);

        libro.setEstatus(dto.getEstatus() != null
                ? Estatus.valueOf(dto.getEstatus().toUpperCase())
                : Estatus.ACTIVO);

        libro.setSinopsis(dto.getSinopsis());

        if (dto.getImagenBase64() != null && !dto.getImagenBase64().isEmpty()) {
            libro.setImagen(Base64.getDecoder().decode(dto.getImagenBase64()));
        }

        if (dto.getAutoresIds() != null && !dto.getAutoresIds().isEmpty()) {
            List<Autores> autores = autorRepository.findAllById(dto.getAutoresIds());
            libro.setAutores(new HashSet<>(autores));
        }

        Libro guardado = libroRepository.save(libro);
        return convertirADTO(guardado);
    }

    
    
   
   public LibroDTO actualizar(String id, LibroDTO dto) {

    Libro libro = libroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

    libro.setTitulo(dto.getTitulo());
    libro.setAnioPublicacion(dto.getAnioPublicacion());
    libro.setEditorial(dto.getEditorial());

    libro.setTotalCopias(dto.getTotalCopias());
    libro.setCopiasDisponibles(dto.getCopiasDisponibles());

    
    if (dto.getCategoriaId() != null) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        libro.setCategoria(categoria);
    }

 
    if (dto.getEstatus() != null) {
        libro.setEstatus(Estatus.valueOf(dto.getEstatus().toUpperCase()));
    }

    if (dto.getSinopsis() != null) {
        libro.setSinopsis(dto.getSinopsis());
    }

   
    if (dto.getImagenBase64() != null && !dto.getImagenBase64().isEmpty()) {
        libro.setImagen(Base64.getDecoder().decode(dto.getImagenBase64()));
    }

    if (dto.getAutoresIds() != null) {
        List<Autores> autores = autorRepository.findAllById(dto.getAutoresIds());
        libro.setAutores(new HashSet<>(autores));
    }

    Libro actualizado = libroRepository.save(libro);
    return convertirADTO(actualizado);
}


   private LibroDTO convertirADTO(Libro libro) {
	    return new LibroDTO(
	        libro.getId(),
	        libro.getTitulo(),
	        libro.getAnioPublicacion(),
	        libro.getEditorial(),
	        libro.getTotalCopias(),
	        libro.getCopiasDisponibles(),
	        libro.getCategoria().getId(),
	        libro.getEstatus().name(),
	        libro.getSinopsis(),
	        libro.getImagen() != null ? Base64.getEncoder().encodeToString(libro.getImagen()) : null,
	        libro.getAutores().stream().map(Autores::getId).toList()
	    );
	}


    public void eliminar(String id) {
        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("Libro no encontrado");
        }
        libroRepository.deleteById(id);
    }

    public LibroDTO cambiarEstatus(String id, String nuevoEstatus) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        try {
            Estatus estatus = Estatus.valueOf(nuevoEstatus.toUpperCase());
            libro.setEstatus(estatus);
            Libro actualizado = libroRepository.save(libro);
            return convertirADTO(actualizado);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estatus inválido. Valores permitidos: ACTIVO, INACTIVO");
        }
    }

   

    
    
    public List<LibroActivoDTO> obtenerLibrosActivos() {
        return libroRepository.findByEstatusAndCopiasDisponiblesGreaterThanEqual(Estatus.ACTIVO, 1)
                .stream()
                .map(libro -> new LibroActivoDTO(libro.getId(), libro.getTitulo()))
                .toList();
    }

    
    public List<LibroActivoDTO> obtenerLibrosActivoss() {
        return libroRepository.findByEstatus(Estatus.ACTIVO)
                .stream()
                .map(libro -> new LibroActivoDTO(libro.getId(), libro.getTitulo()))
                .toList();
    }
    
    
    public List<LibroListadoDTO> listarLibrosActivos(String filtroTitulo) {

        List<Libro> libros;

        if (filtroTitulo == null || filtroTitulo.isBlank()) {
            libros = libroRepository.findByEstatus(Estatus.ACTIVO);
        } else {
            libros = libroRepository.findByEstatusAndTituloContainingIgnoreCase(Estatus.ACTIVO, filtroTitulo);
        }

        return libros.stream().map(libro ->
                new LibroListadoDTO(
                		libro.getId(),                        libro.getTitulo(),
                        libro.getSinopsis(),
                        libro.getCopiasDisponibles(),
                        libro.getImagen() != null ?
                                Base64.getEncoder().encodeToString(libro.getImagen()) : null,
                        libro.getAutores().stream()
                                .map(a -> a.getNombre() + " " + a.getApellidoPaterno())
                                .collect(Collectors.toList())
                )
        ).collect(Collectors.toList());
    }
    
    
    public List<LibroCategoriaDTO> obtenerLibrosPorCategoria(String categoriaId) {
        List<Libro> libros = libroRepository.findByCategoria_Id(categoriaId);

        return libros.stream().map(libro -> {
            List<String> autoresNombres = libro.getAutores()
                                               .stream()
                                               .map(a -> a.getNombre())
                                               .collect(Collectors.toList());

            List<String> autoresIds = libro.getAutores()
                                           .stream()
                                           .map(a -> a.getId())
                                           .collect(Collectors.toList());

            String imagenBase64 = null;
            if (libro.getImagen() != null) {
                imagenBase64 = Base64.getEncoder().encodeToString(libro.getImagen());
            }

            return new LibroCategoriaDTO(
                    libro.getId(),
                    libro.getTitulo(),
                    autoresNombres,
                    autoresIds,
                    libro.getAnioPublicacion(),
                    libro.getEditorial(),
                    libro.getTotalCopias(),
                    libro.getCopiasDisponibles(),
                    libro.getCategoria().getId(),
                    libro.getCategoria().getNombre(),
                    libro.getEstatus().name(),
                    imagenBase64,
                    libro.getSinopsis()
            );
        }).collect(Collectors.toList());
    }

}
