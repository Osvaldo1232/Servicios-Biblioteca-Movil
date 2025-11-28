package com.primaria.app.Service;

import com.primaria.app.DTO.AlumnoDTO;
import com.primaria.app.Model.Alumno;
import com.primaria.app.repository.AlumnoRepository;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AlumnoService {

	
	  @Autowired
	    private ModelMapper modelMapper;

	  
    private final AlumnoRepository estudianteRepository;

    public AlumnoService(AlumnoRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public List<Alumno> findAll() {
        return estudianteRepository.findAll();
    }

    public Optional<Alumno> findById(String id) {
        return estudianteRepository.findById(id);
    }

    public Alumno save(Alumno estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public void deleteById(String id) {
        estudianteRepository.deleteById(id);
    }
    
    public List<AlumnoDTO> listarTodos() {
        return estudianteRepository.findAll().stream()
                .map(alumno -> modelMapper.map(alumno, AlumnoDTO.class))
                .collect(Collectors.toList());
    }
  
    public Alumno buscarPorMatricula(String matricula) {
        return estudianteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new RuntimeException("Estudiante con matrícula '" + matricula + "' no encontrado"));
    }
    
 
    
    public void eliminarPorMatricula(String matricula) {
        Optional<Alumno> estudianteOpt = estudianteRepository.findByMatricula(matricula);
        if (estudianteOpt.isPresent()) {
            estudianteRepository.deleteByMatricula(matricula);
        } else {
            throw new RuntimeException("No se puede eliminar. Estudiante con matrícula '" + matricula + "' no encontrado");
        }
    }
    
    public void eliminarPorId(String id) {
        Optional<Alumno> estudianteOpt = estudianteRepository.findById(id);
        if (estudianteOpt.isPresent()) {
            estudianteRepository.deleteById(id);
        } else {
            throw new RuntimeException("No se puede eliminar. Estudiante con ID '" + id + "' no encontrado");
        }
    }
    
}