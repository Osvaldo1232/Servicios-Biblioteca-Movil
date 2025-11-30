package com.primaria.app.controller;

import com.primaria.app.DTO.AdministradorDTO;
import com.primaria.app.DTO.AlumnoDTO;
import com.primaria.app.DTO.EmpleadoDTO;
import com.primaria.app.DTO.UsuarioInfoDTO;
import com.primaria.app.Model.*;
import com.primaria.app.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import com.primaria.app.DTO.LibroActivoDTO;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Operaciones para registrar usuarios de distintos roles")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/alumno")
    @Operation(summary = "Registrar Alumno")
    public ResponseEntity<?> registrarEstudiante(@RequestBody AlumnoDTO dto) {
        Alumno estudiante = new Alumno();
        estudiante.setNombre(dto.getNombre());
        estudiante.setApellidoMaterno(dto.getApellidoMaterno());
        estudiante.setApellidoPaterno(dto.getApellidoPaterno());
        estudiante.setEstatus(dto.getEstatus());
        estudiante.setEmail(dto.getEmail());
        estudiante.setPassword(dto.getPassword());
        estudiante.setMatricula(dto.getMatricula());
        estudiante.setRol(Rol.ALUMNO);
       
        estudiante.setFechaNacimiento(dto.getFechaNacimiento());
        estudiante.setSexo(dto.getSexo());

        usuarioService.save(estudiante);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Alumno registrado exitosamente");
        response.put("id", estudiante.getId());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/empleado")
    @Operation(summary = "Registrar empleado")
    public ResponseEntity<?> registrarProfesor(@RequestBody EmpleadoDTO dto) {
        Empleado profesor = new Empleado();
        profesor.setNombre(dto.getNombre());
        profesor.setApellidoMaterno(dto.getApellidoMaterno());
       profesor.setApellidoPaterno(dto.getApellidoPaterno());
       profesor.setEstatus(dto.getEstatus());
        profesor.setEmail(dto.getEmail());
        profesor.setPassword(dto.getPassword());
        profesor.setRol(Rol.EMPLEADO);
        
        profesor.setFechaNacimiento(dto.getFechaNacimiento());
        profesor.setSexo(dto.getSexo());
       
        profesor.setTelefono(dto.getTelefono());
        profesor.setClavePresupuestal(dto.getClavePresupuestal());
        usuarioService.save(profesor);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Empleado registrado exitosamente");
        response.put("id", profesor.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/administrador")
    @Operation(summary = "Registrar Administrador")
    public ResponseEntity<?> registrarDirector(@RequestBody AdministradorDTO dto) {
        Administrador director = new Administrador();
        director.setNombre(dto.getNombre());
        director.setApellidoMaterno(dto.getApellidoMaterno());
        director.setApellidoPaterno(dto.getApellidoPaterno());
        director.setEstatus(dto.getEstatus());
        
        director.setEmail(dto.getEmail());
        director.setPassword(dto.getPassword());
        director.setTelefono(dto.getTelefono());
        
        director.setDepartamento(dto.getDepartamento());
        director.setRol(Rol.ADMINISTRADOR);

        director.setFechaNacimiento(dto.getFechaNacimiento());
        director.setSexo(dto.getSexo());

        usuarioService.save(director);
        return ResponseEntity.ok("Administrador registrado exitosamente");
    }
    
    
    @PostMapping("/profesor")
    @Operation(summary = "Registrar profesor")
    public ResponseEntity<?> registrarDocente(@RequestBody EmpleadoDTO dto) {
        Empleado profesor = new Empleado();
        profesor.setNombre(dto.getNombre());
        profesor.setApellidoMaterno(dto.getApellidoMaterno());
       profesor.setApellidoPaterno(dto.getApellidoPaterno());
       profesor.setEstatus(dto.getEstatus());
        profesor.setEmail(dto.getEmail());
        profesor.setPassword(dto.getPassword());
        profesor.setRol(Rol.MAESTRO);
        
        profesor.setFechaNacimiento(dto.getFechaNacimiento());
        profesor.setSexo(dto.getSexo());
       
        profesor.setTelefono(dto.getTelefono());
        profesor.setClavePresupuestal(dto.getClavePresupuestal());
        usuarioService.save(profesor);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Profesor registrado exitosamente");
        response.put("id", profesor.getId());
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Obtener usuario por ID con detalles según el rol")
    @GetMapping("/BuscarUsuario/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable String id) {
        Object usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }
    
    @Operation(summary = " Actualizar estatus de un usuario")
   
    @PatchMapping("/{id}/estatus")
    public ResponseEntity<String> actualizarEstatus(
            @PathVariable("id") String id,
            @RequestParam("estatus") Estatus estatus) {

        boolean actualizado = usuarioService.actualizarEstatus(id, estatus);
        if (actualizado) {
            return ResponseEntity.ok("Estatus actualizado correctamente a " + estatus);
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }
    
    @Operation(
            summary = "Obtiene información del usuario por ID",
            description = "Devuelve el nombre, nombre completo y rol de un usuario utilizando su ID."
    )
    @GetMapping("/info/{id}")
    public UsuarioInfoDTO obtenerInfoUsuario(@PathVariable String id) {
        return usuarioService.obtenerInfoUsuarioPorId(id);
    }
    
    
    @Operation(
            summary = "Obtener usuarios activos",
            description = "Regresa una lista con el ID y el nombre completo de los usuarios con estatus ACTIVO."
        )
        @GetMapping("/activos")
        public List<LibroActivoDTO> listarUsuariosActivos() {
            return usuarioService.obtenerUsuariosActivos();
        }
}
