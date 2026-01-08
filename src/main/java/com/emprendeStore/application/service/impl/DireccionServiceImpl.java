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
        Usuario u = ur.getReferenceById(dto.getIdUsu());
        Pais p = pr.getReferenceById(dto.getIdPais());
        UbicacionNivel1 un1 = dto.getIdUbicacionNivel1() != null ? un1r.getReferenceById(dto.getIdUbicacionNivel1()) : null;
        UbicacionNivel2 un2 = dto.getIdUbicacionNivel2() != null ? un2r.getReferenceById(dto.getIdUbicacionNivel2()) : null;
        UbicacionNivel3 un3 = dto.getIdUbicacionNivel3() != null ? un3r.getReferenceById(dto.getIdUbicacionNivel3()) : null;
        boolean esPrimera = dr.countByUsuarioIdUsu(dto.getIdUsu()) == 0;
        dto.setEsPrincipal(esPrimera);
        Direccion d = dm.toEntity(dto, u, p, un1, un2, un3);
        d = dr.save(d);
        return dm.toDto(d);
    }

    @Override
    public List<DireccionResponseDto> listarDirecciones(Long idUsuario) {
        return dr.findAllConDetallesPorUsuario(idUsuario)
                .stream()
                .map(dm::toDto)
                .toList();
    }

    @Override
    public DireccionResponseDto obtenerDireccionPrincipal(Long idUsuario) {
        return dr.findDireccionPrincipalConDetalles(idUsuario)
                .map(dm::toDto)
                .orElse(null);
    }

    @Override
    @Transactional
    public DireccionResponseDto updateDireccion(Long idDireccion, DireccionRequestDto dto) {
        Direccion d = dr.findById(idDireccion).orElseThrow(() -> new ErrorNegocio("Dirección no encontrada"));
        Pais p = pr.getReferenceById(dto.getIdPais());
        UbicacionNivel1 un1 = dto.getIdUbicacionNivel1() != null ? un1r.getReferenceById(dto.getIdUbicacionNivel1()) : null;
        UbicacionNivel2 un2 = dto.getIdUbicacionNivel2() != null ? un2r.getReferenceById(dto.getIdUbicacionNivel2()) : null;
        UbicacionNivel3 un3 = dto.getIdUbicacionNivel3() != null ? un3r.getReferenceById(dto.getIdUbicacionNivel3()) : null;
        // Preservamos el estado original de 'esPrincipal'
        boolean eraPrincipal = d.getEsPrincipal();
        
        // Actualizamos la entidad con los datos del DTO
        dm.updateEntity(d, dto, p, un1, un2, un3);
        
        // Restauramos el estado original de 'esPrincipal' para ignorar lo que venga en el DTO
        d.setEsPrincipal(eraPrincipal);
        dr.save(d);
        return dm.toDto(d);
    }

    @Override
    public void deleteDireccion(Long idDireccion) {
        if (!dr.existsById(idDireccion)) {
            throw new ErrorNegocio("Direccion no encontrada");
        }
        dr.deleteById(idDireccion);
    }

    @Override
    @Transactional
    public void establecerPrincipal(Long idDireccion) {
        Direccion d = dr.findById(idDireccion).orElseThrow(() -> new ErrorNegocio("Dirección no encontrada"));
        if (d.getEsPrincipal()) return;
        dr.desmarcarTodasLasDelUsuario(d.getUsuario().getIdUsu());
        d.setEsPrincipal(true);
        dr.save(d);
    }

}
