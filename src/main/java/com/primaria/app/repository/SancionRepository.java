package com.primaria.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.primaria.app.Model.Sancion;

public interface SancionRepository extends JpaRepository<Sancion, String> {
	List<Sancion> findByPrestamoId(String prestamoId);



}
