package com.primaria.app.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.primaria.app.DTO.LibrosMasPrestadosDTO;
import com.primaria.app.DTO.PrestamoUsuarioDTO;
import com.primaria.app.DTO.PrestamosResumenDTO;
import com.primaria.app.Model.Prestamo;
import com.primaria.app.repository.PrestamoRepository;

@Service
public class PrestamoServices {
	
	private final PrestamoRepository prestamoRepository;

    public PrestamoServices(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    public List<PrestamosResumenDTO> obtenerTop10FechasConMasPrestamos() {
        return prestamoRepository.obtenerTop10FechasConMasPrestamos()
                .stream()
                .limit(10)
                .toList();
    }
    public List<LibrosMasPrestadosDTO> obtenerTop10LibrosMasPrestados() {
        return prestamoRepository
                .obtenerTop10LibrosMasPrestados()
                .stream()
                .limit(10)
                .toList();
    }
    public List<PrestamoUsuarioDTO> obtenerPrestamosPorUsuario(String usuarioId) {

        List<Prestamo> prestamos = prestamoRepository.findByUsuarioId(usuarioId);

        return prestamos.stream().map(p ->
                new PrestamoUsuarioDTO(
                        p.getLibro().getTitulo(),
                        p.getLibro().getImagen(),
                        p.getCantidad(),
                        p.getEstatus().name(),
                        p.getFechaPrestamo(),
                        p.getFechaDevolucion(),
                        p.getLibro().getAutores()
                                .stream()
                                .map(a -> a.getNombre())
                                .collect(Collectors.toList())
                )
        ).collect(Collectors.toList());
    }
}
