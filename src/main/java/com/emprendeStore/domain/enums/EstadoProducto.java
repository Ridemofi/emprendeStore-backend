package com.emprendeStore.domain.enums;

import com.emprendeStore.application.exception.ErrorNegocio;

public enum EstadoProducto {
    Disponible, Agotado, Bajo, Pausado;

    public static EstadoProducto obtenerDesde(String value) {
        try {
            return EstadoProducto.valueOf(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ErrorNegocio("Estado '" + value + "' no es válido");
        }
    }

}
