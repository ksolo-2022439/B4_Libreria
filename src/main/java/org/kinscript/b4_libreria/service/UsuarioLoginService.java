package org.kinscript.b4_libreria.service;

import org.kinscript.b4_libreria.entity.CookieAuth;
import org.kinscript.b4_libreria.entity.Usuario;
import org.kinscript.b4_libreria.repository.CookieAuthRepository;
import org.kinscript.b4_libreria.repository.UsuarioRepository;
import org.kinscript.b4_libreria.util.HashingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioLoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CookieAuthRepository cookieAuthRepository;

    public void registrarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByCorreoElectronico(usuario.getCorreoElectronico());
        if (usuarioExistente.isPresent()) {
            throw new IllegalStateException("El correo electrónico ya está registrado.");
        }

        String contrasenaHasheada = HashingUtil.sha256(usuario.getContrasena());
        usuario.setContrasena(contrasenaHasheada);

        usuario.setRol(Usuario.Rol.CLIENTE);

        usuarioRepository.save(usuario);
    }

    public Usuario login(String correoElectronico, String contrasenaPlana) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreoElectronico(correoElectronico);

        if (usuarioOpt.isEmpty()) {
            throw new SecurityException("Email o contraseña incorrectos.");
        }

        Usuario usuario = usuarioOpt.get();

        String contrasenaHasheada = HashingUtil.sha256(contrasenaPlana);

        if (contrasenaHasheada.equals(usuario.getContrasena())) {
            return usuario;
        } else {
            throw new SecurityException("Email o contraseña incorrectos.");
        }
    }

    public String createRememberMeToken(Usuario usuario) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(30);

        CookieAuth cookieAuth = new CookieAuth();
        cookieAuth.setUsuario(usuario);
        cookieAuth.setToken(token);
        cookieAuth.setFechaExpiracion(expirationDate);

        cookieAuthRepository.save(cookieAuth);
        return token;
    }

    public Optional<Usuario> findUserByToken(String token) {
        Optional<CookieAuth> cookieAuthOpt = cookieAuthRepository.findByToken(token);

        if (cookieAuthOpt.isPresent()) {
            CookieAuth cookieAuth = cookieAuthOpt.get();
            if (cookieAuth.getFechaExpiracion().isAfter(LocalDateTime.now())) {
                return Optional.of(cookieAuth.getUsuario());
            } else {
                cookieAuthRepository.delete(cookieAuth);
            }
        }
        return Optional.empty();
    }

    public void deleteRememberMeToken(String token) {
        cookieAuthRepository.findByToken(token).ifPresent(cookieAuthRepository::delete);
    }
}