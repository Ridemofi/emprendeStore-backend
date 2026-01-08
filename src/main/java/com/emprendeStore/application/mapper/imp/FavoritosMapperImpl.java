package com.emprendeStore.application.mapper.imp;

import com.emprendeStore.application.mapper.FavoritosMapper;
import com.emprendeStore.domain.model.Favoritos;
import com.emprendeStore.domain.model.Producto;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.web.dto.response.FavoritoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoritosMapperImpl implements FavoritosMapper {

    @Override
    public Favoritos toEntity(Usuario u, Producto p) {
        return Favoritos.builder()
                .usuario(u)
                .producto(p)
                .build();
    }

    @Override
    public FavoritoResponseDto toDto(Favoritos f) {
        return FavoritoResponseDto.builder()
                .idFav(f.getIdFav())
                .idUsu(f.getUsuario().getIdUsu())
                .idPro(f.getProducto().getIdProducto())
                .fechaAgregado(f.getFechaAgregado().toString())
                .build();
    }
}
