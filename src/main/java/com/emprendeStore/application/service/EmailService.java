package com.emprendeStore.application.service;

public interface EmailService {
    void enviarCorreoBienvenida(String destinatario, String nombreUsuario);
}
