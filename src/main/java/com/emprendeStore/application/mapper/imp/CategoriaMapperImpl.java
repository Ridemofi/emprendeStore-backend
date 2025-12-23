package com.emprendeStore.application.mapper.imp;

import com.emprendeStore.application.mapper.CategoriaMapper;
import com.emprendeStore.domain.model.Categoria;
import com.emprendeStore.web.dto.request.CategoriaRequestDTO;
import com.emprendeStore.web.dto.response.CategoriaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoriaMapperImpl implements CategoriaMapper {


    @Override
    public Categoria toEntity(CategoriaRequestDTO dto) {
        return Categoria.builder()
                .imgCat(dto.getImgcat())
                .nombreCat(dto.getNombreCat())
                .build();
    }

    @Override
    public CategoriaResponseDTO toDto(Categoria c) {
        return CategoriaResponseDTO.builder()
                .imgcat(c.getImgCat())
                .idCategoria(c.getIdCategoria())
                .nombreCat(c.getNombreCat())
                .build();
    }
}
