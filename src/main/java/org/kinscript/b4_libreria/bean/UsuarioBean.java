package org.kinscript.b4_libreria.bean;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.kinscript.b4_libreria.entity.Usuario;
import org.kinscript.b4_libreria.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component("usuarioBean")
@RequestScope
@Getter
@Setter
public class UsuarioBean {

    @Autowired
    private UsuarioService usuarioService;

    private Usuario usuario = new Usuario();

    private String confirmarContrasena;

    public String registrar() {
        if (!usuario.getContrasena().equals(confirmarContrasena)) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las contraseñas no coinciden.");
            return null;
        }

        try {
            usuarioService.registrarUsuario(usuario);

            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("exito", "¡Cuenta creada exitosamente! Por favor, inicia sesión.");
            return "/iniciarSesion.xhtml?faces-redirect=true";

        } catch (IllegalStateException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            return null;
        }
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }
}