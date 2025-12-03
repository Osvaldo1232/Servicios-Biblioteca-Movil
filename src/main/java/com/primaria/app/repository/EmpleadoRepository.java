package com.primaria.app.repository;

import com.primaria.app.Model.Empleado;
import com.primaria.app.Model.Rol;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {
    // Puedes agregar consultas personalizadas si es necesario
	
	List<Empleado> findByRol(Rol rol);
}