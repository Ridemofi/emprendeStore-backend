package com.emprendeStore.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UpdateUsuarioRequestDto {
    private String imgusu;
    @Size(min = 3, max = 30, message = "El nombre debe tener entre 3 y 30 caracteres")
    private String nombreReal;
    @Email(message = "El formato del correo no es válido")
    private String correo;
    @Size(max = 11, message = "El número de celular no puede exceder los 11 caracteres")
    private String nroCel;
}
