package com.primaria.app.Service;

import com.primaria.app.DTO.CarreraDTO;
import com.primaria.app.DTO.LibroActivoDTO;
import com.primaria.app.Model.Carrera;
import com.primaria.app.Model.Estatus;
import com.primaria.app.exception.ApiException;
import com.primaria.app.repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarreraService {

    @Autowired
    private CarreraRepository carreraRepository;

    public CarreraDTO crearCarrera(CarreraDTO dto) {
        Optional<Carrera> existente = carreraRepository.findByNombreIgnoreCase(dto.getNombre());
        if (existente.isPresent()) {
            throw new ApiException("Ya existe una carrera con el nombre: " + dto.getNombre(), 400);
        }

        Carrera carrera = new Carrera();
        carrera.setNombre(dto.getNombre());
        carrera.setEstatus(dto.getEstatus() != null ? dto.getEstatus() : Estatus.ACTIVO);

        carrera = carreraRepository.save(carrera);
        return convertirADTO(carrera);
    }

    public CarreraDTO actualizarCarrera(String id, CarreraDTO dto) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new ApiException("Carrera no encontrada con ID: " + id, 404));

        if (!carrera.getNombre().equalsIgnoreCase(dto.getNombre())) {
            Optional<Carrera> existente = carreraRepository.findByNombreIgnoreCase(dto.getNombre());
            if (existente.isPresent()) {
                throw new ApiException("Ya existe una carrera con el nombre: " + dto.getNombre(), 400);
            }
        }

        carrera.setNombre(dto.getNombre());
        carrera.setEstatus(dto.getEstatus());
        carrera = carreraRepository.save(carrera);

        return convertirADTO(carrera);
    }


    public CarreraDTO cambiarEstatus(String id, Estatus nuevoEstatus) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con ID: " + id));

        carrera.setEstatus(nuevoEstatus);
        carrera = carreraRepository.save(carrera);
        return convertirADTO(carrera);
    }

    public List<CarreraDTO> listarTodas() {
        return carreraRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private CarreraDTO convertirADTO(Carrera carrera) {
        return new CarreraDTO(carrera.getId(), carrera.getNombre(), carrera.getEstatus());
    }
    public List<LibroActivoDTO> obtenerLibrosActivos() {
        return carreraRepository.findByEstatus(Estatus.ACTIVO)
                .stream()
                .map(libro -> new LibroActivoDTO(libro.getId(), libro.getNombre()))
                .toList();
    }


    
}
