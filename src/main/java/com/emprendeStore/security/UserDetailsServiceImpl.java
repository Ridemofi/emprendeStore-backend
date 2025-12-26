package com.emprendeStore.security;

import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.domain.repository.EmprendedorRepository;
import com.emprendeStore.domain.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmprendedorRepository emprendedorRepository;
    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(EmprendedorRepository emprendedorRepository, UsuarioRepository usuarioRepository) {
        this.emprendedorRepository = emprendedorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // 1. Intentar buscar como Emprendedor (por correo)
        Optional<Emprendedor> emprendedorOpt = emprendedorRepository.findByCorreoemp(correo);
        if (emprendedorOpt.isPresent()) {
            Emprendedor emp = emprendedorOpt.get();
            return new User(emp.getCorreoemp(), emp.getPasswordempre(), Collections.emptyList());
        }

        // 2. Intentar buscar como Usuario (solo por correo)
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
        if (usuarioOpt.isPresent()) {
            Usuario usu = usuarioOpt.get();
            return new User(usu.getCorreo(), usu.getPassword(), Collections.emptyList());
        }

        // 3. Si no se encuentra en ninguno
        throw new UsernameNotFoundException("Usuario o Emprendedor no encontrado con el correo: " + correo);
    }
}
