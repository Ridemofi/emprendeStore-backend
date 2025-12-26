package com.emprendeStore.application.mapper.imp;

import com.emprendeStore.application.mapper.AuthMapper;
import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.web.dto.response.AuthEmprendedorResponseDto;
import com.emprendeStore.web.dto.response.AuthUsuarioResponseDto;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class AuthMapperImpl implements AuthMapper {

    @Override
    public AuthEmprendedorResponseDto toAuthEmprendedorDto(Emprendedor emprendedor, String token) {
        String imagenBase64 = null;
        if (emprendedor.getImgenemp() != null && emprendedor.getImgenemp().length > 0) {
            imagenBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(emprendedor.getImgenemp());
        }

        return AuthEmprendedorResponseDto.builder()
                .idemp(emprendedor.getIdempre())
                .nombep(emprendedor.getNombreemp())
                .correo(emprendedor.getCorreoemp())
                .nrocell(emprendedor.getNrocellemp())
                .fechaingreso(emprendedor.getFecharegistroemp())
                .imgemp(imagenBase64)
                .token(token)
                .rol(emprendedor.getRol())
                .build();
    }

    @Override
    public AuthUsuarioResponseDto toAuthUsuarioDto(Usuario usuario, String token) {
        String imagenBase64 = null;
        if (usuario.getImgUsu() != null && usuario.getImgUsu().length > 0) {
            imagenBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(usuario.getImgUsu());
        }

        return AuthUsuarioResponseDto.builder()
                .id(usuario.getIdUsu())
                .nombreReal(usuario.getNombReal())
                .correo(usuario.getCorreo())
                .nroCel(usuario.getNroCel())
                .fechaRegistro(usuario.getFechaRegistro())
                .img(imagenBase64)
                .token(token)
                .rol(usuario.getRol())
                .build();
    }
}
