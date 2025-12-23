package com.emprendeStore.application.exception;

import java.time.LocalDateTime;

public record ErrorResponse(String mensaje, LocalDateTime fecha) {}
