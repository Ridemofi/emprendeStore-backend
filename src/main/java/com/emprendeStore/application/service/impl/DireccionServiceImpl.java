package com.emprendeStore.application.service.impl;

import com.emprendeStore.application.exception.ErrorNegocio;
import com.emprendeStore.application.mapper.DireccionMapper;
import com.emprendeStore.application.service.DireccionService;
import com.emprendeStore.domain.model.*;
import com.emprendeStore.domain.repository.*;
import com.emprendeStore.web.dto.request.DireccionRequestDto;
import com.emprendeStore.web.dto.response.DireccionResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DireccionServiceImpl implements DireccionService {
    private final DireccionRepository dr;
    private final DireccionMapper dm;
    private final UsuarioRepository ur;
    private final PaisRepository pr;
    private final UbicacionNivel1Repository un1r;
    private final UbicacionNivel2Repository un2r;
    private final UbicacionNivel3Repository un3r;


    @Override
    @Transactional
    public DireccionResponseDto saveDireccion(DireccionRequestDto dto) {
        Usuario u = ur.findById(dto.getIdUsu()).orElseThrow(() -> new ErrorNegocio("Usuario no encontrado"));
        Pais p = pr.findById(dto.getIdPais()).orElseThrow(() -> new ErrorNegocio("Pais no encontrado"));
        
        UbicacionNivel1 un1 = dto.getIdUbicacionNivel1() != null ? 
                un1r.findById(dto.getIdUbicacionNivel1()).orElse(null) : null;
        UbicacionNivel2 un2 = dto.getIdUbicacionNivel2() != null ? 
                un2r.findById(dto.getIdUbicacionNivel2()).orElse(null) : null;
        UbicacionNivel3 un3 = dto.getIdUbicacionNivel3() != null ? 
                un3r.findById(dto.getIdUbicacionNivel3()).orElse(null) : null;

        List<Direccion> direccionesUsuario = dr.findByUsuarioIdUsu(u.getIdUsu());
        if (direccionesUsuario.isEmpty()) {
            dto.setEsPrincipal(true);
        } else if (dto.isEsPrincipal()) {
            desmarcarPrincipal(u.getIdUsu());
        }

        Direccion d = dm.toEntity(dto, u, p, un1, un2, un3);
        dr.save(d);
        return dm.toDto(d);
    }

    @Override
    public List<DireccionResponseDto> listarDirecciones(Long idUsuario) {
        return dr.findByUsuarioIdUsu(idUsuario)
                .stream()
                .map(dm::toDto)
                .toList();
    }

    @Override
    public DireccionResponseDto obtenerDireccionPrincipal(Long idUsuario) {
        return dr.findByUsuarioIdUsuAndEsPrincipalTrue(idUsuario)
                .stream()
                .findFirst()
                .map(dm::toDto)
                .orElse(null);
    }

    @Override
    @Transactional
    public DireccionResponseDto updateDireccion(Long idDireccion, DireccionRequestDto dto) {
        Direccion d = dr.findById(idDireccion).orElseThrow(() -> new ErrorNegocio("Dirección no encontrada"));
        Pais p = pr.findById(dto.getIdPais()).orElseThrow(() -> new ErrorNegocio("Pais no encontrado"));
        UbicacionNivel1 un1 = dto.getIdUbicacionNivel1() != null ? un1r.findById(dto.getIdUbicacionNivel1()).orElse(null) : null;
        UbicacionNivel2 un2 = dto.getIdUbicacionNivel2() != null ? un2r.findById(dto.getIdUbicacionNivel2()).orElse(null) : null;
        UbicacionNivel3 un3 = dto.getIdUbicacionNivel3() != null ? un3r.findById(dto.getIdUbicacionNivel3()).orElse(null) : null;
        if (dto.isEsPrincipal() && !d.getEsPrincipal()) {
            desmarcarPrincipal(d.getUsuario().getIdUsu());
        }
        dm.updateEntity(d, dto, p, un1, un2, un3);
        dr.save(d);
        return dm.toDto(d);
    }

    @Override
    public void deleteDireccion(Long idDireccion) {
        Direccion d = dr.findById(idDireccion).orElseThrow(() -> new ErrorNegocio("Direccion no encontrada"));
        dr.delete(d);
    }

    private void desmarcarPrincipal(Long idUsuario) {
        List<Direccion> principales = dr.findByUsuarioIdUsuAndEsPrincipalTrue(idUsuario);
        for (Direccion dir : principales) {
            dir.setEsPrincipal(false);
            dr.save(dir);
        }
    }
}
