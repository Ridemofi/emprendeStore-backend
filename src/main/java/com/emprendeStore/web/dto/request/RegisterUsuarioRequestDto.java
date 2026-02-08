package com.emprendeStore.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class RegisterUsuarioRequestDto {
    @NotBlank(message = "El nombre real no puede estar vacío")
    @Size(max = 40)
    private String nombreReal;

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El formato del correo no es válido")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El correo debe tener un dominio válido con punto (ejemplo@dominio.com)")
    private String correo;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 3, message = "La contraseña debe tener al menos 3 caracteres")
    private String password;
}
