package com.emprendeStore.application.mapper.imp;

import com.emprendeStore.application.mapper.UsuarioMapper;
import com.emprendeStore.domain.enums.Rol;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.web.dto.request.RegisterUsuarioRequestDto;
import com.emprendeStore.web.dto.request.UpdateUsuarioRequestDto;
import com.emprendeStore.web.dto.response.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toEntity(RegisterUsuarioRequestDto dto) {
        return Usuario.builder()
                .nombReal(dto.getNombreReal())
                .correo(dto.getCorreo())
                .password(dto.getPassword())
                .rol(Rol.USUARIO)
                .build();
    }

    @Override
    public void updateEntity(UpdateUsuarioRequestDto dtoUpdate, Usuario u) {
        // La imagen del usuario se actualiza únicamente mediante el endpoint específico con Cloudinary
        if (dtoUpdate.getNombreReal() != null) {
            u.setNombReal(dtoUpdate.getNombreReal());
        }
        if (dtoUpdate.getNroCel() != null) {
            u.setNroCel(dtoUpdate.getNroCel());
        }
        if (dtoUpdate.getCorreo() != null) {
            u.setCorreo(dtoUpdate.getCorreo());
        }
    }

    @Override
    public UsuarioResponseDto toDto(Usuario u) {
        String img = u.getImagenUsu();
        return UsuarioResponseDto.builder()
                .id(u.getIdUsu())
                .img(img)
                .nombreReal(u.getNombReal())
                .correo(u.getCorreo())
                .nroCel(u.getNroCel())
                .fechaRegistro(u.getFechaRegistro())
                .build();
    }
}
