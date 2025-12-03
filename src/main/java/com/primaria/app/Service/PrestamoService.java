
package com.primaria.app.Service;

import com.primaria.app.DTO.PrestamoDTO;
import com.primaria.app.DTO.PrestamoDetalleDTO;
import com.primaria.app.Model.EstatusPrestamo;

import java.time.LocalDate;
import java.util.List;

public interface PrestamoService {
    PrestamoDTO crearPrestamo(PrestamoDTO dto);           
    PrestamoDTO apartarLibro(PrestamoDTO dto);             
    PrestamoDTO devolverPrestamo(String prestamoId, Integer cantidadDevuelta); 
    PrestamoDTO obtenerPorId(String id);
    List<PrestamoDetalleDTO> obtenerDetalles();           
    
    PrestamoDTO confirmarApartado(String prestamoId);

    PrestamoDTO cancelarPrestamo(String prestamoId);
    
    List<PrestamoDetalleDTO> filtrarPrestamos(LocalDate fechaPrestamo,
                                              LocalDate fechaDevolucion,
                                              String usuarioNombre,
                                              String libroTitulo,
                                              EstatusPrestamo estatus);
    void actualizarEstatusVencidos();                      
    
    
    
    public void actualizarEstatusVencido(String prestamoId, String motivo);

}
