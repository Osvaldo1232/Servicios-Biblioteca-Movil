package com.primaria.app.controller;

import com.primaria.app.DTO.LibroActivoDTO;
import com.primaria.app.DTO.LibroCategoriaDTO;
import com.primaria.app.DTO.LibroDTO;
import com.primaria.app.DTO.LibroListadoDTO;
import com.primaria.app.Model.Libro;
import com.primaria.app.Service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("libros")
@Tag(name = "Libros", description = "Operaciones relacionadas con los libros de la biblioteca")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @Operation(summary = "Listar todos los libros", description = "Obtiene una lista de todos los libros con su categoría y autores")
    @GetMapping
    public ResponseEntity<List<LibroCategoriaDTO>> listarTodos() {
        List<LibroCategoriaDTO> libros = libroService.listarTodos();
        return ResponseEntity.ok(libros);
    }

    @Operation(summary = "Obtener libro por ID", description = "Devuelve un libro según su identificador")
    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> obtenerPorId(@PathVariable String id) {
        Optional<LibroDTO> libro = libroService.obtenerPorId(id);
        return libro.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo libro", description = "Registra un nuevo libro con su categoría y autores")
    @PostMapping
    public ResponseEntity<LibroDTO> crear(@RequestBody LibroDTO dto) {
        LibroDTO nuevo = libroService.crear(dto);
        return ResponseEntity.ok(nuevo);
    }

    @Operation(summary = "Actualizar libro", description = "Modifica los datos de un libro existente")
    @PutMapping("/{id}")
    public ResponseEntity<LibroDTO> actualizar(@PathVariable String id, @RequestBody LibroDTO dto) {
        LibroDTO actualizado = libroService.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar libro", description = "Elimina un libro por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cambiar estatus de un libro", description = "Cambia el estatus de un libro (ACTIVO o INACTIVO)")
    @PatchMapping("/{id}/estatus/{nuevoEstatus}")
    public ResponseEntity<LibroDTO> cambiarEstatus(@PathVariable String id, @PathVariable String nuevoEstatus) {
        LibroDTO actualizado = libroService.cambiarEstatus(id, nuevoEstatus);
        return ResponseEntity.ok(actualizado);
    }
    @Operation(summary = "Libros activos", description = "Libros activos")
    @GetMapping("/activosl")
    public List<LibroActivoDTO> obtenerLibrosActivos() {
        return libroService.obtenerLibrosActivos();
    }
    
    @GetMapping("/LibrosActivos")
    @Operation(
            summary = "Listar libros activos",
            description = "Devuelve solo libros activos. Puedes enviar un filtro por título opcional."
    )
    public List<LibroListadoDTO> listarLibrosActivos(
            @Parameter(description = "Título a filtrar (opcional)")
            @RequestParam(required = false) String titulo
    ) {
        return libroService.listarLibrosActivos(titulo);
    }
    @Operation(
            summary = "Listar libros con parametro de categoria",
            description = "Devuelve solo libros que pertenencen a una categoria"
    )
    
    @GetMapping("/categoria/{idCategoria}")
    public List<LibroCategoriaDTO> obtenerPorCategoria(@PathVariable String idCategoria) {
        return libroService.obtenerLibrosPorCategoria(idCategoria);
    }

}

