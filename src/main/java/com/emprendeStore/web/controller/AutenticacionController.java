package com.emprendeStore.web.controller;

import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.domain.repository.EmprendedorRepository;
import com.emprendeStore.domain.repository.UsuarioRepository;
import com.emprendeStore.security.JwtTokenProvider;
import com.emprendeStore.web.dto.request.LoginEmprendedorRequestDto;
import com.emprendeStore.web.dto.request.LoginUsuarioRequestDto;
import com.emprendeStore.web.dto.response.AuthEmprendedorResponseDto;
import com.emprendeStore.web.dto.response.AuthUsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AutenticacionController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmprendedorRepository emprendedorRepository;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/loginempre")
    public ResponseEntity<AuthEmprendedorResponseDto> loginEmpre(@RequestBody LoginEmprendedorRequestDto loginDto) {

        // A. Autenticar (Valida usuario y contraseña)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getCorreo(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // B. Generar Token
        String token = jwtTokenProvider.generateToken(authentication);

        // C. Recuperar los datos del Emprendedor de la BD
        Emprendedor emprendedor = emprendedorRepository.findByCorreoemp(loginDto.getCorreo())
                .orElseThrow(() -> new RuntimeException("Emprendedor no encontrado"));

        // D. Convertir la imagen de bytes a Base64 (String)
        String imagenBase64 = null;
        if (emprendedor.getImgenemp() != null) {
            imagenBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(emprendedor.getImgenemp());
        }

        // E. Construir el DTO con todos los datos
        AuthEmprendedorResponseDto response = AuthEmprendedorResponseDto.builder()
                .idemp(emprendedor.getIdempre())
                .nombep(emprendedor.getNombreemp())
                .correo(emprendedor.getCorreoemp())
                .nrocell(emprendedor.getNrocellemp())
                .fechaingreso(emprendedor.getFecharegistroemp())
                .imgemp(imagenBase64)
                .token(token)
                .rol(emprendedor.getRol()) // Agregamos el rol
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/loginusu")
    public ResponseEntity<AuthUsuarioResponseDto> loginUsu(@RequestBody LoginUsuarioRequestDto loginDto) {

        // A. Autenticar
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getIdentificador(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // B. Generar Token
        String token = jwtTokenProvider.generateToken(authentication);

        // C. Recuperar los datos del Usuario de la BD
        Usuario usuario = usuarioRepository.findByCorreoOrNomUsu(loginDto.getIdentificador(), loginDto.getIdentificador())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // D. Convertir la imagen de bytes a Base64 (String)
        String imagenBase64 = null;
        if (usuario.getImgUsu() != null) {
            imagenBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(usuario.getImgUsu());
        }

        // E. Construir el DTO con todos los datos
        AuthUsuarioResponseDto response = AuthUsuarioResponseDto.builder()
                .id(usuario.getIdUsu())
                .nombreReal(usuario.getNombReal())
                .nomUsu(usuario.getNomUsu())
                .correo(usuario.getCorreo())
                .nroCel(usuario.getNroCel())
                .fechaRegistro(usuario.getFechaRegistro())
                .img(imagenBase64)
                .token(token)
                .rol(usuario.getRol())
                .build();

        return ResponseEntity.ok(response);
    }
}
