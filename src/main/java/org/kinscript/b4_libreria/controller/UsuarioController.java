package org.kinscript.b4_libreria.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.kinscript.b4_libreria.entity.Usuario;
import org.kinscript.b4_libreria.service.iUsuarioService;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@ViewScoped
@Data
public class UsuarioController implements Serializable {

    @Autowired
    private iUsuarioService usuarioService;

    private List<Usuario> usuarios;
    private Usuario usuarioSeleccionado;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @PostConstruct
    public void init() {
        cargarDatos();
    }

    public void cargarDatos() {
        this.usuarios = usuarioService.listarUsuarios();
    }

    public void agregarUsuario() {
        this.usuarioSeleccionado = new Usuario();
    }

    public Usuario.Rol[] getRoles() {
        return Usuario.Rol.values();
    }

    public void guardarUsuario() {
        logger.info("Usuario a guardar: " + this.usuarioSeleccionado);

        if (this.usuarioSeleccionado.getIdUsuario() == null) {
            Usuario nuevo = this.usuarioService.guardarUsuario(this.usuarioSeleccionado);
            this.usuarios.add(nuevo);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario Agregado"));
        } else {
            this.usuarioService.guardarUsuario(this.usuarioSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario Actualizado"));
        }

        cargarDatos();
        PrimeFaces.current().executeScript("PF('ventanaModalUsuario').hide();");
        PrimeFaces.current().ajax().update("formulario-usuarios:tabla-usuarios", "formulario-usuarios:mensaje-emergente");
        this.usuarioSeleccionado = null;
    }

    public void eliminarUsuario() {
        logger.info("Usuario a eliminar: " + this.usuarioSeleccionado);
        this.usuarioService.eliminarUsuario(this.usuarioSeleccionado);
        this.usuarios.remove(this.usuarioSeleccionado);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario Eliminado"));
        PrimeFaces.current().ajax().update("formulario-usuarios:tabla-usuarios", "formulario-usuarios:mensaje-emergente");
        this.usuarioSeleccionado = null;
    }
}