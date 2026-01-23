package com.emprendeStore.security;

import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.domain.repository.EmprendedorRepository;
import com.emprendeStore.domain.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
            // Asignamos la autoridad (rol) basada en el campo rol de la entidad
            return new User(emp.getCorreoemp(), emp.getPasswordempre(), 
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + emp.getRol().name())));
        }

        // 2. Intentar buscar como Usuario (solo por correo)
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
        if (usuarioOpt.isPresent()) {
            Usuario usu = usuarioOpt.get();
            // Asignamos la autoridad (rol) basada en el campo rol de la entidad
            return new User(usu.getCorreo(), usu.getPassword(), 
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usu.getRol().name())));
        }

        // 3. Si no se encuentra en ninguno
        throw new UsernameNotFoundException("Usuario o Emprendedor no encontrado con el correo: " + correo);
    }
    
    // Métodos optimizados para buscar directamente si ya sabemos el rol
    public UserDetails loadUserByUsernameAndRole(String correo, String role) {
        if ("ROLE_EMPRENDEDOR".equals(role) || "EMPRENDEDOR".equals(role)) {
             Emprendedor emp = emprendedorRepository.findByCorreoemp(correo)
                     .orElseThrow(() -> new UsernameNotFoundException("Emprendedor no encontrado: " + correo));
             return new User(emp.getCorreoemp(), emp.getPasswordempre(), 
                     Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + emp.getRol())));
        } else {
             Usuario usu = usuarioRepository.findByCorreo(correo)
                     .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));
             return new User(usu.getCorreo(), usu.getPassword(), 
                     Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usu.getRol())));
        }
    }
}
