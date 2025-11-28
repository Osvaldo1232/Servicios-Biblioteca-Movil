
package com.primaria.app.Service;

import com.primaria.app.DTO.PrestamoDTO;
import com.primaria.app.DTO.PrestamoDetalleDTO;
import com.primaria.app.Model.EstatusPrestamo;

import java.time.LocalDate;
import java.util.List;

public interface PrestamoService {
    PrestamoDTO crearPrestamo(PrestamoDTO dto);            // crear préstamo normal
    PrestamoDTO apartarLibro(PrestamoDTO dto);             // crear apartado (reserva)
    PrestamoDTO devolverPrestamo(String prestamoId, Integer cantidadDevuelta); // devolver
    PrestamoDTO obtenerPorId(String id);
    List<PrestamoDetalleDTO> obtenerDetalles();            // lista completa con detalles
    
    PrestamoDTO confirmarApartado(String prestamoId);

    List<PrestamoDetalleDTO> filtrarPrestamos(LocalDate fechaPrestamo,
                                              LocalDate fechaDevolucion,
                                              String usuarioNombre,
                                              String libroTitulo,
                                              EstatusPrestamo estatus);
    void actualizarEstatusVencidos();                      // marcar vencidos (cron o manual)
}
