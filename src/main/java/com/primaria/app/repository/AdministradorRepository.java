package com.primaria.app.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.primaria.app.Model.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, String> {
  
}