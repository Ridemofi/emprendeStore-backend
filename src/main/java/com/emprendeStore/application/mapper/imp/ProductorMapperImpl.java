package com.emprendeStore.application.mapper.imp;

import com.emprendeStore.application.mapper.ProductorMapper;
import com.emprendeStore.domain.model.Categoria;
import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.model.Producto;
import com.emprendeStore.web.dto.request.ProductoRequestDTO;
import com.emprendeStore.web.dto.request.UpdateProductoRequestDto;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductorMapperImpl implements ProductorMapper {

    @Override
    public Producto toEntity(ProductoRequestDTO prodto, Categoria c, Emprendedor e) {
        return Producto.builder()
                .emprendedor(e)
                .categoria(c)
                .nombreProd(prodto.getNombreProd())
                .descrip(prodto.getDescrip())
                .precio(prodto.getPrecio())
                .stock(prodto.getStock())
                .imgPro(prodto.getImgpro())
                .build();
    }

    @Override
    public ProductoResponseDTO toDto(Producto p) {
        return ProductoResponseDTO.builder()
                .id(p.getIdProducto())
                .nombreProd(p.getNombreProd())
                .descrip(p.getDescrip())
                .precio(p.getPrecio())
                .stock(p.getStock())
                .imgpro(p.getImgPro())
                .estadoProducto(p.getEstadoProducto().name())
                .idCategoria(p.getCategoria().getIdCategoria())
                .nombreCategoria(p.getCategoria().getNombreCat())
                .idEmprendedor(p.getEmprendedor().getIdempre())
                .build();
    }

    @Override
    public void updateEntity(UpdateProductoRequestDto dto, Producto p) {
        if (dto.getNombreProd() != null) {
            p.setNombreProd(dto.getNombreProd());
        }
        if (dto.getDescrip() != null) {
            p.setDescrip(dto.getDescrip());
        }
        if (dto.getPrecio() != null) {
            p.setPrecio(dto.getPrecio());
        }
        if (dto.getStock() != null) {
            p.setStock(dto.getStock());
        }
    }
}
