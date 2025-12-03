	package com.primaria.app.controller;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.primaria.app.DTO.AlumnoDTO;
import com.primaria.app.DTO.EmpleadoDTO;
import com.primaria.app.Model.Alumno;
import com.primaria.app.Model.Empleado;
import com.primaria.app.Model.Rol;
import com.primaria.app.Model.Usuario;
import com.primaria.app.Service.UsuarioService;
import com.primaria.app.repository.EmpleadoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/Profesores")
@Tag(name = "Profesores", description = "Operaciones para Profesores")
public class ProfesorController {
    
    private final UsuarioService usuarioService;
    
    private final EmpleadoRepository empleadoRepository;
    
    public ProfesorController(UsuarioService usuarioService, EmpleadoRepository empleadoRepository) {
        this.usuarioService = usuarioService;
        this.empleadoRepository=empleadoRepository;
    }
    
    
    @GetMapping
    @Operation(summary = "Obtener todos los empleados por rol")
    public ResponseEntity<List<Empleado>> obtenerEmpleadosPorRol() {
        List<Empleado> empleados = empleadoRepository.findByRol(Rol.EMPLEADO);
        return ResponseEntity.ok(empleados);
    }

    
    @GetMapping("/profesores")
    @Operation(summary = "Obtener todos los empleados por rol")
    public ResponseEntity<List<Empleado>> obtenerProfesoresPorRol() {
        List<Empleado> empleados = empleadoRepository.findByRol(Rol.MAESTRO);
        return ResponseEntity.ok(empleados);
    }
    
    @PutMapping("/empleado/{id}")
    @Operation(summary = " Actualizar Empleado")
    public ResponseEntity<?> actualizarProfesor(@PathVariable String id, @RequestBody EmpleadoDTO dto) {
        Optional<Usuario> optUsuario = usuarioService.findById(id);
        if (optUsuario.isEmpty() || !(optUsuario.get() instanceof Empleado)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("empleado no encontrado");
        }
        Empleado profesor = (Empleado) optUsuario.get();
        profesor.setNombre(dto.getNombre());
        profesor.setApellidoMaterno(dto.getApellidoMaterno());
        profesor.setApellidoPaterno(dto.getApellidoPaterno());
        profesor.setEmail(dto.getEmail());
        profesor.setFechaNacimiento(dto.getFechaNacimiento());
        profesor.setSexo(dto.getSexo());
        profesor.setTelefono(dto.getTelefono());
        profesor.setClavePresupuestal(dto.getClavePresupuestal());
        usuarioService.update(profesor, dto.getPassword());
        return ResponseEntity.ok("Profesor actualizado exitosamente");
    }
    
    
    @PutMapping("/alumno/{id}")
    @Operation(summary = " Actualizar Alumno")
    public ResponseEntity<?> actualizarAlumno(@PathVariable String id, @RequestBody AlumnoDTO dto) {
        Optional<Usuario> optUsuario = usuarioService.findById(id);
        if (optUsuario.isEmpty() || !(optUsuario.get() instanceof Alumno)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("empleado no encontrado");
        }
        Alumno profesor = (Alumno) optUsuario.get();
        profesor.setNombre(dto.getNombre());
        profesor.setApellidoMaterno(dto.getApellidoMaterno());
        profesor.setApellidoPaterno(dto.getApellidoPaterno());
        profesor.setEmail(dto.getEmail());
        profesor.setFechaNacimiento(dto.getFechaNacimiento());
        profesor.setSexo(dto.getSexo());
        profesor.setMatricula(dto.getMatricula());
        usuarioService.update(profesor, dto.getPassword());
        return ResponseEntity.ok("Profesor actualizado exitosamente");
    }
    
    
    @Operation(summary = "Obtener todos los alumnos")
    @GetMapping("/alumnos")
    public ResponseEntity<List<Usuario>> obtenerAlumnos() {
        return ResponseEntity.ok(usuarioService.obtenerAlumnos());
    }

   
}