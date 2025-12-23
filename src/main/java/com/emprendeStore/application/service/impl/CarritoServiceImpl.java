package com.emprendeStore.application.service.impl;

import com.emprendeStore.application.exception.ErrorNegocio;
import com.emprendeStore.application.mapper.CarritoMapper;
import com.emprendeStore.application.service.CarritoService;
import com.emprendeStore.domain.model.Carrito;
import com.emprendeStore.domain.model.DetalleCarrito;
import com.emprendeStore.domain.model.Producto;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.domain.repository.CarritoRepository;
import com.emprendeStore.domain.repository.DetalleCarritoRepository;
import com.emprendeStore.domain.repository.ProductoRepository;
import com.emprendeStore.domain.repository.UsuarioRepository;
import com.emprendeStore.web.dto.response.CarritoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CarritoServiceImpl implements CarritoService {

    private final DetalleCarritoRepository dcr;
    private final CarritoRepository cr;
    private final CarritoMapper cm;
    private final UsuarioRepository ur;
    private final ProductoRepository pr;

    private Carrito obtenerOCrearCarrito(Long idUsuario) {
        return cr.findByUsuarioIdUsu(idUsuario)
                .orElseGet(() -> {
                    Usuario usuario = ur.findById(idUsuario)
                            .orElseThrow(() -> new ErrorNegocio("Usuario no encontrado"));
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setUsuario(usuario);
                    return cr.save(nuevoCarrito);
                });
    }

    @Override
    @Transactional
    public CarritoResponseDto obtenerCarrito(Long idUsuario) {
        Carrito carrito = obtenerOCrearCarrito(idUsuario);
        return cm.toDto(carrito);
    }

    @Override
    @Transactional
    public CarritoResponseDto agregarProducto(Long idUsuario, Long idProducto) {
        Carrito carrito = obtenerOCrearCarrito(idUsuario);
        Producto producto = pr.findById(idProducto)
                .orElseThrow(() -> new ErrorNegocio("Producto no encontrado"));

        Optional<DetalleCarrito> detalleOpt = carrito.getDetalles().stream()
                .filter(d -> d.getProducto().getIdProducto().equals(idProducto))
                .findFirst();

        if (detalleOpt.isPresent()) {
            DetalleCarrito detalle = detalleOpt.get();
            detalle.setCantidad(detalle.getCantidad() + 1);
        } else {
            DetalleCarrito nuevoDetalle = DetalleCarrito.builder()
                    .carrito(carrito)
                    .producto(producto)
                    .cantidad(1)
                    .precioUnitario(producto.getPrecio())
                    .build();
            carrito.getDetalles().add(nuevoDetalle);
        }

        cr.save(carrito);
        return cm.toDto(carrito);
    }

    @Override
    @Transactional
    public CarritoResponseDto actualizarCantidadItem(Long idUsuario, Long idDetalleCarrito, int nuevaCantidad) {
        if (nuevaCantidad <= 0) {
            return removerItem(idUsuario, idDetalleCarrito);
        }

        DetalleCarrito detalle = dcr.findById(idDetalleCarrito)
                .orElseThrow(() -> new ErrorNegocio("Ítem del carrito no encontrado"));

        if (!detalle.getCarrito().getUsuario().getIdUsu().equals(idUsuario)) {
            throw new ErrorNegocio("Acceso denegado. No puedes modificar ítems de otro usuario.");
        }

        detalle.setCantidad(nuevaCantidad);
        dcr.save(detalle);

        return obtenerCarrito(idUsuario);
    }

    @Override
    @Transactional
    public CarritoResponseDto removerItem(Long idUsuario, Long idDetalleCarrito) {
        DetalleCarrito detalle = dcr.findById(idDetalleCarrito)
                .orElseThrow(() -> new ErrorNegocio("Ítem del carrito no encontrado"));

        if (!detalle.getCarrito().getUsuario().getIdUsu().equals(idUsuario)) {
            throw new ErrorNegocio("Acceso denegado");
        }
        detalle.getCarrito().getDetalles().remove(detalle);
        dcr.delete(detalle);
        return obtenerCarrito(idUsuario);
    }
}