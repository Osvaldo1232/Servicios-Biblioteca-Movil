package com.primaria.app.repository;

import com.primaria.app.Model.Alumno;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, String> {
    // Puedes agregar consultas personalizadas si es necesario
	Optional<Alumno> findByMatricula(String matricula);
	void deleteByMatricula(String matricula);
	Optional<Alumno> findById(String id);
	
}