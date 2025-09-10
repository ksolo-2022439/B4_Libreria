package org.kinscript.b4_libreria.service;

import org.kinscript.b4_libreria.entity.Usuario;
import java.util.List;

public interface iUsuarioService {
    List<Usuario> listarUsuarios();
    Usuario buscarUsuario(Integer idUsuario);
    Usuario guardarUsuario(Usuario usuario);
    void eliminarUsuario(Usuario usuario);
}