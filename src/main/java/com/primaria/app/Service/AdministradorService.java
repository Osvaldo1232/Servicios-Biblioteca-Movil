package com.primaria.app.Service;
import com.primaria.app.Model.Administrador;
import com.primaria.app.repository.AdministradorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdministradorService {

    private final AdministradorRepository directorRepository;

    public AdministradorService(AdministradorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public List<Administrador> findAll() {
        return directorRepository.findAll();
    }

    public Optional<Administrador> findById(String id) {
        return directorRepository.findById(id);
    }

    public Administrador save(Administrador director) {
        return directorRepository.save(director);
    }

    public void deleteById(String id) {
        directorRepository.deleteById(id);
    }
}