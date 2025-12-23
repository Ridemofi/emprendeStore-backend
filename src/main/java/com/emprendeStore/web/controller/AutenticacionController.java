package com.emprendeStore.web.controller;

import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.repository.EmprendedorRepository;
import com.emprendeStore.security.JwtTokenProvider;
import com.emprendeStore.web.dto.request.LoginEmprendedorRequestDto;
import com.emprendeStore.web.dto.response.AuthEmprendedorResponseDto;
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
    private final EmprendedorRepository emprendedorRepository; // <--- 3. Inyectar Repositorio

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
            // Asumiendo que es JPEG o PNG. Esto permite ponerlo directo en <img src={imgemp} />
            imagenBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(emprendedor.getImgenemp());
        }

        // E. Construir el DTO con todos los datos
        AuthEmprendedorResponseDto response = AuthEmprendedorResponseDto.builder()
                .idemp(emprendedor.getIdempre())
                .nombep(emprendedor.getNombreemp())
                .correo(emprendedor.getCorreoemp())
                .nrocell(emprendedor.getNrocellemp())
                .fechaingreso(emprendedor.getFecharegistroemp())
                .imgemp(imagenBase64) // Aquí va la imagen convertida
                .token(token)
                .build();

        return ResponseEntity.ok(response);
    }
}