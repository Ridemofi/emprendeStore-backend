package com.emprendeStore.application.service.impl;

import com.emprendeStore.application.mapper.FavoritosMapper;
import com.emprendeStore.application.mapper.ProductorMapper;
import com.emprendeStore.application.service.FavoritoService;
import com.emprendeStore.domain.model.Favoritos;
import com.emprendeStore.domain.model.Producto;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.domain.repository.FavoritosRepository;
import com.emprendeStore.domain.repository.ProductoRepository;
import com.emprendeStore.domain.repository.UsuarioRepository;
import com.emprendeStore.web.dto.request.FavoritoRequestDto;
import com.emprendeStore.web.dto.response.FavoritoResponseDto;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoritoServiceImpl implements FavoritoService {
    private final FavoritosMapper fm;
    private final FavoritosRepository fr;
    private final ProductorMapper pm;
    private final UsuarioRepository ur;
    private final ProductoRepository pr;

    @Override
    @Transactional
    public FavoritoResponseDto savefav(FavoritoRequestDto dto) {
        Usuario u = ur.findById(dto.getIdUsu())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Producto p = pr.findById(dto.getIdPro())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        if (fr.findByUsuarioIdUsuAndProductoIdProducto(u.getIdUsu(), p.getIdProducto()).isPresent()) {
            throw new RuntimeException("Ya existe en favoritos");
        }
        Favoritos f=fm.toEntity(dto, u, p);
        fr.save(f);
        return fm.toDto(f);
    }

    @Override
    public List<ProductoResponseDTO> listarFavoritosxUsu(Long id) {
        List<Producto> p = fr.findProductosFavoritosPorUsuario(id);
        return p.stream()
                .map(pm::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deletefav(Long idUsu, Long idPro) {
        fr.deleteByUsuarioIdUsuAndProductoIdProducto(idUsu, idPro);
    }

}
