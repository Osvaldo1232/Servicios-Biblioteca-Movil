package com.primaria.app.Service;

import com.primaria.app.DTO.LibroActivoDTO;
import com.primaria.app.DTO.LibroCategoriaDTO;
import com.primaria.app.DTO.LibroDTO;
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

                    // Lista de nombres de autores
                    libro.getAutores().stream()
                            .map(a -> a.getNombre() + " " + a.getApellidoPaterno())
                            .collect(Collectors.toList()),

                    // Lista de IDs de autores  <-- NUEVO
                    libro.getAutores().stream()
                            .map(a -> a.getId())
                            .collect(Collectors.toList()),

                    libro.getAnioPublicacion(),
                    libro.getEditorial(),
                    libro.getCopiasDisponibles(),
                    libro.getCategoria().getId(),
                    libro.getCategoria().getNombre(),
                    libro.getEstatus().name()
            ))
            .collect(Collectors.toList());
}



    // Obtener libro por ID
    public Optional<LibroDTO> obtenerPorId(String id) {
        return libroRepository.findById(id)
                .map(this::convertirADTO);
    }

    // Crear libro
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

        // Imagen Base64 → byte[]
        if (dto.getImagenBase64() != null && !dto.getImagenBase64().isEmpty()) {
            libro.setImagen(Base64.getDecoder().decode(dto.getImagenBase64()));
        }

        // Autores
        if (dto.getAutoresIds() != null && !dto.getAutoresIds().isEmpty()) {
            List<Autores> autores = autorRepository.findAllById(dto.getAutoresIds());
            libro.setAutores(new HashSet<>(autores));
        }

        Libro guardado = libroRepository.save(libro);
        return convertirADTO(guardado);
    }

    
    // Actualizar libro
   
   public LibroDTO actualizar(String id, LibroDTO dto) {

    Libro libro = libroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

    libro.setTitulo(dto.getTitulo());
    libro.setAnioPublicacion(dto.getAnioPublicacion());
    libro.setEditorial(dto.getEditorial());

    libro.setTotalCopias(dto.getTotalCopias());
    libro.setCopiasDisponibles(dto.getCopiasDisponibles());

    // Categoría
    if (dto.getCategoriaId() != null) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        libro.setCategoria(categoria);
    }

    // Estatus
    if (dto.getEstatus() != null) {
        libro.setEstatus(Estatus.valueOf(dto.getEstatus().toUpperCase()));
    }

    if (dto.getSinopsis() != null) {
        libro.setSinopsis(dto.getSinopsis());
    }

    // Imagen
    if (dto.getImagenBase64() != null && !dto.getImagenBase64().isEmpty()) {
        libro.setImagen(Base64.getDecoder().decode(dto.getImagenBase64()));
    }

    // Autores
    if (dto.getAutoresIds() != null) {
        List<Autores> autores = autorRepository.findAllById(dto.getAutoresIds());
        libro.setAutores(new HashSet<>(autores));
    }

    Libro actualizado = libroRepository.save(libro);
    return convertirADTO(actualizado);
}


    // Conversión a DTO
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


    // Eliminar libro
    public void eliminar(String id) {
        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("Libro no encontrado");
        }
        libroRepository.deleteById(id);
    }

    // Cambiar estatus
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
}
