package com.emprendeStore.security;

import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.repository.EmprendedorRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmprendedorRepository emprendedorRepository;

    public UserDetailsServiceImpl(EmprendedorRepository emprendedorRepository) {
        this.emprendedorRepository = emprendedorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Emprendedor emprendedor = emprendedorRepository.findByCorreoemp(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Emprendedor no encontrado con el correo: " + correo));
        // Convertimos tu Emprendedor al objeto User estándar de Spring Security.
        // Usamos tu correo como 'username' y tu password como 'password'.
        // Collections.emptyList() es para los roles (authorities), que por ahora no estamos manejando.
        return new User(emprendedor.getCorreoemp(), emprendedor.getPasswordempre(), Collections.emptyList());
    }
}