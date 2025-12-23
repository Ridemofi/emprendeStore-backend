package com.emprendeStore.application.mapper.imp;

import com.emprendeStore.application.exception.ErrorNegocio;
import com.emprendeStore.application.mapper.UsuarioMapper;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.web.dto.request.RegisterUsuarioRequestDto;
import com.emprendeStore.web.dto.request.UpdateUsuarioRequestDto;
import com.emprendeStore.web.dto.response.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toEntity(RegisterUsuarioRequestDto dto) {
        return Usuario.builder()
                .imgUsu(dto.getImg())
                .nombReal(dto.getNombreReal())
                .nomUsu(dto.getNomUsu())
                .correo(dto.getCorreo())
                .nroCel(dto.getNroCel())
                .password(dto.getPassword())
                .build();
    }

    @Override
    public void updateEntity(UpdateUsuarioRequestDto dtoUpdate, Usuario u) {
        if (dtoUpdate.getImgusu() != null && !dtoUpdate.getImgusu().isEmpty()) {
            try {
                byte[] imgb = Base64.getDecoder().decode(dtoUpdate.getImgusu());
                u.setImgUsu(imgb);
            } catch (IllegalArgumentException e) {
                throw new ErrorNegocio("Error al decodificar la imagen Base64.");
            }
        }
        if (dtoUpdate.getNombreReal() != null) {
            u.setNombReal(dtoUpdate.getNombreReal());
        }
        if (dtoUpdate.getNroCel() != null) {
            u.setNroCel(dtoUpdate.getNroCel());
        }
        if (dtoUpdate.getNomUsu() != null) {
            u.setNomUsu(dtoUpdate.getNomUsu());
        }
        if (dtoUpdate.getCorreo() != null) {
            u.setCorreo(dtoUpdate.getCorreo());
        }
    }

    @Override
    public UsuarioResponseDto toDto(Usuario u) {
        String img = null;
        if (u.getImgUsu() != null && u.getImgUsu().length > 0) {
            img = Base64.getEncoder().encodeToString(u.getImgUsu());
        }
        return UsuarioResponseDto.builder()
                .id(u.getIdUsu())
                .img(img)
                .nombreReal(u.getNombReal())
                .nomUsu(u.getNomUsu())
                .correo(u.getCorreo())
                .nroCel(u.getNroCel())
                .fechaRegistro(u.getFechaRegistro())
                .build();
    }
}
