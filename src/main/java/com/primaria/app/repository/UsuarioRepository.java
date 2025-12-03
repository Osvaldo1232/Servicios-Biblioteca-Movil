package com.primaria.app.repository;

import com.primaria.app.Model.Estatus;
import com.primaria.app.Model.Rol;
import com.primaria.app.Model.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
	Optional<Usuario> findByEmail(String email);
	List<Usuario> findByRol(Rol rol);
	List<Usuario> findByEstatus(Estatus estatus);
}