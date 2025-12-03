package com.primaria.app.Service;

import com.primaria.app.DTO.SancionPrestamoDTO;
import com.primaria.app.Model.Sancion;
import com.primaria.app.repository.SancionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SancionService {

    private final SancionRepository sancionRepository;

    public SancionService(SancionRepository sancionRepository) {
        this.sancionRepository = sancionRepository;
    }

    public List<SancionPrestamoDTO> obtenerSancionesPorPrestamo(String prestamoId) {
        List<Sancion> sanciones = sancionRepository.findByPrestamoId(prestamoId);

        return sanciones.stream().map(s -> {
            String nombreCompleto = s.getPrestamo().getUsuario().getNombre() + " " +
                                    s.getPrestamo().getUsuario().getApellidoPaterno() + " " +
                                    s.getPrestamo().getUsuario().getApellidoMaterno();
            return new SancionPrestamoDTO(s.getMotivo(), nombreCompleto, s.getFechaSancion());
        }).toList();
    }
}
