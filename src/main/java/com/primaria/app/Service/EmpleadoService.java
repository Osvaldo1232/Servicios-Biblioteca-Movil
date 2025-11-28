package com.primaria.app.Service;


import com.primaria.app.Model.Empleado;
import com.primaria.app.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmpleadoService {

    private final EmpleadoRepository profesorRepository;

    public EmpleadoService(EmpleadoRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    public List<Empleado> findAll() {
        return profesorRepository.findAll();
    }

    public Optional<Empleado> findById(String id) {
        return profesorRepository.findById(id);
    }

    public Empleado save(Empleado profesor) {
        return profesorRepository.save(profesor);
    }

    public void deleteById(String id) {
        profesorRepository.deleteById(id);
    }
}