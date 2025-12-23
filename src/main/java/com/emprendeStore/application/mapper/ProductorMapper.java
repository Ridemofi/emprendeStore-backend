package com.emprendeStore.application.mapper;


import com.emprendeStore.domain.model.Categoria;
import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.model.Producto;
import com.emprendeStore.web.dto.request.ProductoRequestDTO;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;

public interface ProductorMapper {
    Producto toEntity (ProductoRequestDTO prodto, Categoria c, Emprendedor e);
    ProductoResponseDTO toDto(Producto p);

}
