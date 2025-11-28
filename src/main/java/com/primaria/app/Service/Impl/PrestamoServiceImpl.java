
package com.primaria.app.Service.Impl;

import com.primaria.app.DTO.PrestamoDTO;
import com.primaria.app.DTO.PrestamoDetalleDTO;
import com.primaria.app.Model.*;
import com.primaria.app.repository.*;
import com.primaria.app.Service.PrestamoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository,
                               UsuarioRepository usuarioRepository,
                               LibroRepository libroRepository) {
        this.prestamoRepository = prestamoRepository;
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
    }

    // ---------------- Crear préstamo normal ----------------
    @Transactional
    @Override
    public PrestamoDTO crearPrestamo(PrestamoDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));

        if (dto.getCantidad() == null || dto.getCantidad() <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cantidad inválida");

        if (libro.getCopiasDisponibles() == null || libro.getCopiasDisponibles() < dto.getCantidad())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay suficientes copias disponibles");

        // Descontar copias
        libro.setCopiasDisponibles(libro.getCopiasDisponibles() - dto.getCantidad());
        libroRepository.save(libro);

        // Crear y guardar préstamo
        Prestamo prestamo = new Prestamo(usuario, libro, dto.getCantidad(),
                dto.getFechaDevolucion() != null ? dto.getFechaDevolucion() : LocalDate.now().plusDays(7)); // default 7 días

        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setEstatus(EstatusPrestamo.PRESTADO);

        Prestamo saved = prestamoRepository.save(prestamo);
        return convertirADTO(saved);
    }

    // ---------------- Apartar (reserva) ----------------
    @Transactional
    @Override
    public PrestamoDTO apartarLibro(PrestamoDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));

        if (libro.getCopiasDisponibles() == null || libro.getCopiasDisponibles() < (dto.getCantidad() == null ? 1 : dto.getCantidad()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay copias disponibles para apartar");

        // Descontar copias de inmediato para reservar
        int cantidad = dto.getCantidad() == null ? 1 : dto.getCantidad();
        libro.setCopiasDisponibles(libro.getCopiasDisponibles() - cantidad);
        libroRepository.save(libro);

        Prestamo apartado = new Prestamo(usuario, libro, cantidad, dto.getFechaDevolucion() != null ? dto.getFechaDevolucion() : LocalDate.now().plusDays(3));
        apartado.setEstatus(EstatusPrestamo.APARTADO);
        apartado.setFechaPrestamo(LocalDate.now());

        Prestamo saved = prestamoRepository.save(apartado);
        return convertirADTO(saved);
    }

    // ---------------- Devolver ----------------
    @Transactional
    @Override
    public PrestamoDTO devolverPrestamo(String prestamoId, Integer cantidadDevuelta) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Préstamo no encontrado"));

        if (cantidadDevuelta == null || cantidadDevuelta <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cantidad devuelta inválida");

        int pendiente = prestamo.getCantidad() - prestamo.getCantidadDevuelta();
        if (cantidadDevuelta > pendiente)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cantidad devuelta mayor a pendiente");

        // Actualizar préstamo
        prestamo.setCantidadDevuelta(prestamo.getCantidadDevuelta() + cantidadDevuelta);
        prestamo.actualizarEstatusPorDevolucion();

        // Actualizar inventario del libro
        Libro libro = prestamo.getLibro();
        libro.setCopiasDisponibles(libro.getCopiasDisponibles() + cantidadDevuelta);
        libroRepository.save(libro);

        // Si se completó la devolución, fijar fecha de devolución actual
        if (prestamo.getEstatus() == EstatusPrestamo.DEVUELTO) {
            prestamo.setFechaDevolucion(LocalDate.now());
        }

        Prestamo updated = prestamoRepository.save(prestamo);
        return convertirADTO(updated);
    }

    // ---------------- Obtener por id ----------------
    @Override
    public PrestamoDTO obtenerPorId(String id) {
        Prestamo p = prestamoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Préstamo no encontrado"));

        // verificar vencimiento al consultarlo
        p.verificarVencimiento();
        prestamoRepository.save(p);

        return convertirADTO(p);
    }

    // ---------------- Listar detalles simples ----------------
    @Override
    public List<PrestamoDetalleDTO> obtenerDetalles() {
        return prestamoRepository.findAll()
                .stream()
                .peek(p -> {
                    p.verificarVencimiento();
                })
                .map(this::toDetalleDTO)
                .collect(Collectors.toList());
    }

    // ---------------- Filtrado (parámetros opcionales) ----------------
    @Override
    public List<PrestamoDetalleDTO> filtrarPrestamos(LocalDate fechaPrestamo,
                                                     LocalDate fechaDevolucion,
                                                     String usuarioNombre,
                                                     String libroTitulo,
                                                     EstatusPrestamo estatus) {

        return prestamoRepository.findAll()
                .stream()
                .peek(p -> p.verificarVencimiento())
                .filter(p -> fechaPrestamo == null || p.getFechaPrestamo().equals(fechaPrestamo))
                .filter(p -> fechaDevolucion == null || p.getFechaDevolucion().equals(fechaDevolucion))
                .filter(p -> usuarioNombre == null || (p.getUsuario().getNombre() + " " + p.getUsuario().getApellidoPaterno()).toLowerCase().contains(usuarioNombre.toLowerCase()))
                .filter(p -> libroTitulo == null || p.getLibro().getTitulo().toLowerCase().contains(libroTitulo.toLowerCase()))
                .filter(p -> estatus == null || p.getEstatus() == estatus)
                .map(this::toDetalleDTO)
                .collect(Collectors.toList());
    }

    // ---------------- Actualizar estatus vencidos (puede invocarse por cron) ----------------
    @Override
    @Transactional
    public void actualizarEstatusVencidos() {
        List<Prestamo> todos = prestamoRepository.findAll();
        LocalDate hoy = LocalDate.now();
        for (Prestamo p : todos) {
            if (p.getEstatus() != EstatusPrestamo.DEVUELTO && p.getEstatus() != EstatusPrestamo.DEVUELTO
                    && p.getFechaDevolucion() != null && p.getFechaDevolucion().isBefore(hoy)) {
                p.setEstatus(EstatusPrestamo.VENCIDO);
                prestamoRepository.save(p);
            }
        }
    }
    
    @Transactional
    @Override
    public PrestamoDTO confirmarApartado(String prestamoId) {

        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Préstamo no encontrado"));

        if (prestamo.getEstatus() != EstatusPrestamo.APARTADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El préstamo no está en estado APARTADO, no puede confirmarse.");
        }

        // Cambiar a PRESTADO
        prestamo.setEstatus(EstatusPrestamo.PRESTADO);

        // Puedes actualizar la fecha de préstamo al momento que se entrega físicamente
        prestamo.setFechaPrestamo(LocalDate.now());

        // Puedes configurar nueva fecha de devolución (ej. 7 días desde hoy)
        prestamo.setFechaDevolucion(LocalDate.now().plusDays(7));

        Prestamo actualizado = prestamoRepository.save(prestamo);
        return convertirADTO(actualizado);
    }


    // ---------------- Helpers ----------------
    private PrestamoDTO convertirADTO(Prestamo p) {
        PrestamoDTO dto = new PrestamoDTO();
        dto.setId(p.getId());
        dto.setUsuarioId(p.getUsuario() != null ? p.getUsuario().getId() : null);
        dto.setLibroId(p.getLibro() != null ? p.getLibro().getId() : null);
        dto.setCantidad(p.getCantidad());
        dto.setCantidadDevuelta(p.getCantidadDevuelta());
        dto.setFechaPrestamo(p.getFechaPrestamo());
        dto.setFechaDevolucion(p.getFechaDevolucion());
        dto.setEstatus(p.getEstatus());
        return dto;
    }

    private PrestamoDetalleDTO toDetalleDTO(Prestamo p) {
        String nombre = p.getUsuario() != null ? p.getUsuario().getNombre() : null;
        String ap = p.getUsuario() != null ? p.getUsuario().getApellidoPaterno() : null;
        String am = p.getUsuario() != null ? p.getUsuario().getApellidoMaterno() : null;

        return new PrestamoDetalleDTO(
                p.getId(),
                nombre,
                ap,
                am,
                p.getLibro() != null ? p.getLibro().getTitulo() : null,
                p.getCantidad(),
                p.getFechaPrestamo(),
                p.getFechaDevolucion(),
                p.getEstatus()
        );
    }
}
