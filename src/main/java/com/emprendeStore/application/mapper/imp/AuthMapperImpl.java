package com.emprendeStore.application.mapper.imp;

import com.emprendeStore.application.mapper.AuthMapper;
import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.web.dto.response.AuthEmprendedorResponseDto;
import com.emprendeStore.web.dto.response.AuthUsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AuthMapperImpl implements AuthMapper {

    @Override
    public AuthEmprendedorResponseDto toAuthEmprendedorDto(Emprendedor e, String token) {
        return AuthEmprendedorResponseDto.builder()
                .idemp(e.getIdempre())
                .nombep(e.getNombreemp())
                .correo(e.getCorreoemp())
                .nrocell(e.getNrocellemp())
                .fechaingreso(e.getFecharegistroemp())
                .imgemp(e.getImgempre())
                .saldo(e.getSaldo())
                .token(token)
                .rol(e.getRol())
                .build();
    }

    @Override
    public AuthUsuarioResponseDto toAuthUsuarioDto(Usuario u, String token) {
        return AuthUsuarioResponseDto.builder()
                .id(u.getIdUsu())
                .nombreReal(u.getNombReal())
                .correo(u.getCorreo())
                .nroCel(u.getNroCel())
                .fechaRegistro(u.getFechaRegistro())
                .img(u.getImagenUsu())
                .token(token)
                .rol(u.getRol())
                .build();
    }

}
