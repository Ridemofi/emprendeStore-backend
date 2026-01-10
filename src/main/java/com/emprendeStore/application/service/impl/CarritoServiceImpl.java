package com.emprendeStore.application.service.impl;

import com.emprendeStore.application.exception.ErrorNegocio;
import com.emprendeStore.application.mapper.CarritoMapper;
import com.emprendeStore.application.mapper.ProductorMapper;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CarritoServiceImpl implements CarritoService {

    private final DetalleCarritoRepository dcr;
    private final CarritoRepository cr;
    private final CarritoMapper cm;
    private final UsuarioRepository ur;
    private final ProductoRepository pr;
    private final ProductorMapper pm;

    private Carrito obtenerOCrearCarrito(Long idUsuario) {
        return cr.findByUsuarioWithDetalles(idUsuario)
                .orElseGet(() -> {
                    //getReferenceById para no gastar recursos trayendo datos del usuario
                    Usuario usuario = ur.getReferenceById(idUsuario);
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setUsuario(usuario);
                    return cr.save(nuevoCarrito);
                });
    }

    @Override
    @Transactional
    public CarritoResponseDto getCarrito(Long idUsuario) {
        Carrito c = obtenerOCrearCarrito(idUsuario);
        return cm.toDto(c);
    }

    @Override
    @Transactional
    public CarritoResponseDto agregarProducto(Long idUsuario, Long idProducto) {
        Carrito carrito = obtenerOCrearCarrito(idUsuario);
        Optional<DetalleCarrito> detalleOpt = carrito.getDetalles().stream()
                .filter(d -> d.getProducto().getIdProducto().equals(idProducto))
                .findFirst();

        if (detalleOpt.isPresent()) {
            DetalleCarrito detalle = detalleOpt.get();
            detalle.setCantidad(detalle.getCantidad() + 1);
        } else {
            Producto producto = pr.findById(idProducto).orElseThrow(() -> new ErrorNegocio("Producto no encontrado"));
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
    public CarritoResponseDto updateCantidadItem(Long idUsuario, Long idDetalleCarrito, int nuevaCantidad) {
        if (nuevaCantidad <= 0) {
            return removerItem(idUsuario, idDetalleCarrito);
        }

        // Optimización: Usar una query directa para verificar propiedad y actualizar, en lugar de findById + save
        int updated = dcr.actualizarCantidad(idUsuario, idDetalleCarrito, nuevaCantidad);
        if (updated == 0) {
             // Si no actualizó nada, puede ser que no exista o no sea del usuario.
             // Hacemos la comprobación lenta solo si falla la rápida.
             if (!dcr.existsById(idDetalleCarrito)) {
                 throw new ErrorNegocio("Ítem del carrito no encontrado");
             }
             // Si existe pero no actualizó, es porque no pertenece al usuario (según la query)
             throw new ErrorNegocio("Acceso denegado. No puedes modificar ítems de otro usuario.");
        }

        return getCarrito(idUsuario);
    }

    @Override
    @Transactional
    public CarritoResponseDto removerItem(Long idUsuario, Long idDetalleCarrito) {
        // Optimización: Borrado directo con validación de usuario en la misma query
        int deleted = dcr.borrarItemDeUsuario(idUsuario, idDetalleCarrito);
        
        if (deleted == 0) {
             if (!dcr.existsById(idDetalleCarrito)) {
                 throw new ErrorNegocio("Ítem del carrito no encontrado");
             }
             throw new ErrorNegocio("Acceso denegado");
        }
        
        // Refrescamos el carrito para devolver el estado actual
        // Nota: Como borramos directamente en BD, el objeto Carrito en memoria (si lo tuviéramos) estaría desactualizado.
        // Al llamar a getCarrito se hace una nueva select fresca.
        return getCarrito(idUsuario);
    }

    @Override
    public BigDecimal getSubtotalCarritoXUsuario(Long idUsuario) {
        return dcr.obtenerSubtotalSeleccionadosPorUsuario(idUsuario);
    }

    @Override
    @Transactional
    public void updateSeleccionItem(Long idUsuario, Long idDetalleCarrito, boolean seleccionado) {
        int filasActualizadas = dcr.actualizarSeleccion(idUsuario, idDetalleCarrito, seleccionado);
        if (filasActualizadas == 0) throw new ErrorNegocio("Error al actualizar ítem");
    }

    @Override
    public BigDecimal calcularCostoEnvio(Long idUsuario) {
        return dcr.calcularCostoEnvioPorUsuario(idUsuario);
    }

    @Override
    @Transactional
    public void limpiarCarrito(Long idUsuario) {
        dcr.deleteSeleccionadosPorUsuario(idUsuario);
    }

    @Override
    public CarritoResponseDto getProductosSeleccionados(Long idUsuario) {
        Carrito c = obtenerOCrearCarrito(idUsuario);
        List<DetalleCarrito> detallesSeleccionados = dcr.findDetallesSeleccionadosPorUsuario(idUsuario);
        // Creamos una copia del carrito pero solo con los detalles seleccionados
        Carrito carritoFiltrado = new Carrito();
        carritoFiltrado.setIdCarrito(c.getIdCarrito());
        carritoFiltrado.setUsuario(c.getUsuario());
        carritoFiltrado.setFechaCreacion(c.getFechaCreacion());
        carritoFiltrado.setDetalles(detallesSeleccionados);
        return cm.toDto(carritoFiltrado);
    }
}
