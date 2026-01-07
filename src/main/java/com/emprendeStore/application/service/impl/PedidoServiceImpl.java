package com.emprendeStore.application.service.impl;

import com.emprendeStore.application.exception.ErrorNegocio;
import com.emprendeStore.application.mapper.PedidoMapper;
import com.emprendeStore.application.mapper.VentaMapper;
import com.emprendeStore.application.service.CarritoService;
import com.emprendeStore.application.service.PedidoService;
import com.emprendeStore.application.service.VentaService;
import com.emprendeStore.domain.model.*;
import com.emprendeStore.domain.repository.*;
import com.emprendeStore.web.dto.request.PedidoRequestDto;
import com.emprendeStore.web.dto.response.PedidoResponseDto;
import com.emprendeStore.web.dto.response.VentaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final DetalleCarritoRepository dcr;
    private final UsuarioRepository ur;
    private final DireccionRepository dr;
    private final PedidoRepository peRepo;
    private final CarritoService cs;
    private final PedidoMapper pm;
    private final VentaService vs;
    private final VentaMapper vm;

    @Override
    @Transactional
    public PedidoResponseDto crearPedido(PedidoRequestDto request) {
        Usuario u = ur.findById(request.getIdUsu()).orElseThrow(() -> new ErrorNegocio("Usuario no encontrado"));
        Direccion dire = dr.findById(request.getIdDireccion()).orElseThrow(() -> new ErrorNegocio("Dirección no encontrada"));
        List<DetalleCarrito> detallesSeleccionados = dcr.findDetallesSeleccionadosPorUsuario(u.getIdUsu());
        if (detallesSeleccionados.isEmpty()) {
            throw new ErrorNegocio("No hay productos seleccionados para comprar");
        }
        BigDecimal costoEnvio = dcr.calcularCostoEnvioPorUsuario(u.getIdUsu());

        String idTransaccion = generarCodigoPedido();
        
        Pedido pe = pm.toEntity(request, u, idTransaccion, costoEnvio, crearSnapshotDireccion(dire));
        
        pe = peRepo.save(pe);

        Map<Emprendedor, List<DetalleCarrito>> itemsPorEmprendedor = detallesSeleccionados.stream()
                .collect(Collectors.groupingBy(dC -> dC.getProducto().getEmprendedor()));

        List<Venta> ventasGeneradas = new ArrayList<>();
        BigDecimal subtotalGlobalAcumulado = BigDecimal.ZERO;

        for (Map.Entry<Emprendedor, List<DetalleCarrito>> entry : itemsPorEmprendedor.entrySet()) {
            Emprendedor empre = entry.getKey();
            List<DetalleCarrito> items = entry.getValue();

            Venta v = vs.generarVenta(pe, empre, items);
            ventasGeneradas.add(v);
            
            subtotalGlobalAcumulado = subtotalGlobalAcumulado.add(v.getSubtotal());
        }

        pe.setSubtotalGlobal(subtotalGlobalAcumulado);
        pe.setTotalPagado(subtotalGlobalAcumulado.add(costoEnvio));
        peRepo.save(pe);

        cs.limpiarCarrito(u.getIdUsu());

        List<VentaResponseDto> ventasDto = ventasGeneradas.stream()
                .map(vm::toDto)
                .toList();

        return pm.toDto(pe, ventasDto);
    }

    private String generarCodigoPedido() {
        String caracteres = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int longitud = 5;
        StringBuilder sb = new StringBuilder("#ORD-");
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < longitud; i++) {
            int index = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(index));
        }
        return sb.toString();
    }

    private String crearSnapshotDireccion(Direccion d) {
        String ubicacion1 = d.getUbicacionNivel1() != null ? d.getUbicacionNivel1().getNombre() : "";
        String ubicacion2 = d.getUbicacionNivel2() != null ? d.getUbicacionNivel2().getNombre() : "";
        String ubicacion3 = d.getUbicacionNivel3() != null ? d.getUbicacionNivel3().getNombre() : "";
        String pais = d.getPais() != null ? d.getPais().getNombrePais() : "";
        String codigoPostal = d.getCodigoPostal() != null ? d.getCodigoPostal() : "";

        return String.format("%s, %s, %s, %s, %s, %s - Tel: %s - CP: %s - %s",
                d.getNombreContacto(),
                d.getDireccion1(),
                ubicacion3,
                ubicacion2,
                ubicacion1,
                pais,
                d.getTelefonoContacto(),
                codigoPostal,
                d.getDireccion2() != null ? d.getDireccion2() : "");
    }
}
