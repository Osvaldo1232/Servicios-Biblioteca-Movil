package com.primaria.app.controller;
import io.swagger.v3.oas.annotations.Parameter;

import com.primaria.app.DTO.LibrosMasPrestadosDTO;
import com.primaria.app.DTO.PrestamoDTO;
import com.primaria.app.DTO.PrestamoDetalleDTO;
import com.primaria.app.DTO.PrestamosResumenDTO;
import com.primaria.app.Model.EstatusPrestamo;
import com.primaria.app.Service.PrestamoService;
import com.primaria.app.Service.PrestamoServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("prestamos")
@CrossOrigin(origins = "*")
@Tag(name = "Gestión de Préstamos", description = "Operaciones para registrar, devolver y consultar préstamos de libros")
public class PrestamoController {

	
	@Autowired
    private  PrestamoService prestamoService;
    
	@Autowired
    private  PrestamoServices prestamoServices;

  
   
    
    @Operation(
            summary = "Obtener top 10 fechas con más préstamos",
            description = "Devuelve las 10 fechas en las que se registraron más préstamos, acompañadas del total de préstamos por fecha."
        )
        
        @GetMapping("/top10-fechas")
        public List<PrestamosResumenDTO> obtenerTop10FechasConMasPrestamos() {
            return prestamoServices.obtenerTop10FechasConMasPrestamos();
        }
    
    @Operation(
            summary = "Obtener los 10 libros más prestados",
            description = "Devuelve una lista con los 10 libros que han sido prestados con mayor frecuencia, incluyendo el nombre del libro y el total de préstamos."
    )
   
    @GetMapping("/top10-libros")
    public List<LibrosMasPrestadosDTO> obtenerTop10Libros() {
        return prestamoServices.obtenerTop10LibrosMasPrestados();
    }

    

   
    @Operation(
            summary = "Crear préstamo",
            description = "Registra un nuevo préstamo en el sistema."
    )
    @PostMapping("/crear")
    public ResponseEntity<PrestamoDTO> crear(@RequestBody PrestamoDTO dto) {
        return ResponseEntity.ok(prestamoService.crearPrestamo(dto));
    }

    @Operation(
            summary = "Apartar libro",
            description = "Aparta un libro sin entregarlo físicamente todavía."
    )
    @PostMapping("/apartar")
    public ResponseEntity<PrestamoDTO> apartar(@RequestBody PrestamoDTO dto) {
        return ResponseEntity.ok(prestamoService.apartarLibro(dto));
    }

    @Operation(
            summary = "Devolver préstamo",
            description = "Registra la entrega total o parcial de un préstamo."
    )
    @PostMapping("/{id}/devolver")
    public ResponseEntity<PrestamoDTO> devolver(
            @PathVariable String id,
            @RequestParam Integer cantidad
    ) {
        return ResponseEntity.ok(prestamoService.devolverPrestamo(id, cantidad));
    }

    @Operation(
            summary = "Obtener préstamo por ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PrestamoDTO> obtener(@PathVariable String id) {
        return ResponseEntity.ok(prestamoService.obtenerPorId(id));
    }

    @Operation(
            summary = "Obtener lista detallada de préstamos",
            description = "Devuelve una vista detallada de todos los préstamos."
    )
    @GetMapping("/detalles")
    public ResponseEntity<List<PrestamoDetalleDTO>> detalles() {
        return ResponseEntity.ok(prestamoService.obtenerDetalles());
    }

    @Operation(
            summary = "Filtrar préstamos",
            description = "Permite filtrar préstamos por fecha, usuario, libro o estatus.",
            parameters = {
                    @Parameter(name = "fechaPrestamo", description = "Fecha del préstamo (YYYY-MM-DD)"),
                    @Parameter(name = "fechaDevolucion", description = "Fecha de devolución (YYYY-MM-DD)"),
                    @Parameter(name = "usuarioNombre", description = "Nombre del usuario"),
                    @Parameter(name = "libroTitulo", description = "Título del libro"),
                    @Parameter(name = "estatus", description = "Estatus del préstamo")
            }
    )
    @GetMapping("/filtrar")
    public ResponseEntity<List<PrestamoDetalleDTO>> filtrar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaPrestamo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDevolucion,
            @RequestParam(required = false) String usuarioNombre,
            @RequestParam(required = false) String libroTitulo,
            @RequestParam(required = false) EstatusPrestamo estatus
    ) {
        return ResponseEntity.ok(
                prestamoService.filtrarPrestamos(fechaPrestamo, fechaDevolucion, usuarioNombre, libroTitulo, estatus)
        );
    }

    @Operation(
            summary = "Actualizar estatus de préstamos vencidos",
            description = "Ejecuta manualmente la actualización de préstamos con fecha vencida."
    )
    @PostMapping("/actualizar-vencidos")
    public ResponseEntity<Void> actualizarVencidos() {
        prestamoService.actualizarEstatusVencidos();
        return ResponseEntity.ok().build();
    }
    
    @Operation(
            summary = "Confirmar apartado",
            description = "Cambia el estatus de un apartado para indicar que el libro ha sido entregado al usuario.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID del préstamo apartado que se quiere confirmar",
                            required = true
                    )
            }
    )
    @PutMapping("/confirmar-apartado/{id}")
    public ResponseEntity<PrestamoDTO> confirmarApartado(@PathVariable String id) {
        return ResponseEntity.ok(prestamoService.confirmarApartado(id));
    }

}
