package org.kinscript.b4_libreria.bean;

import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.kinscript.b4_libreria.entity.Usuario;
import org.kinscript.b4_libreria.service.UsuarioService;
import org.kinscript.b4_libreria.util.CookieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Setter
@Component("sessionBean")
@SessionScope
@Getter
public class SessionBean implements Serializable {

    @Autowired
    private transient UsuarioService usuarioService;

    private Usuario usuarioLogueado;

    public boolean isLoggedIn() {
        return this.usuarioLogueado != null;
    }

    public String logout() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        String rememberMeToken = CookieHelper.getCookieValue(request, "remember-me-token");
        if (rememberMeToken != null) {
            usuarioService.deleteRememberMeToken(rememberMeToken);
            CookieHelper.removeCookie(response, "remember-me-token");
        }

        CookieHelper.removeCookie(response, "user-landing-page");

        externalContext.invalidateSession();

        return "/iniciarSesion.xhtml?faces-redirect=true";
    }

    public String getUsuarioIniciales() {
        if (usuarioLogueado != null && usuarioLogueado.getNombreUsuario() != null && !usuarioLogueado.getNombreUsuario().isEmpty()) {
            String[] partes = usuarioLogueado.getNombreUsuario().trim().split("\\s+");
            if (partes.length > 1) {
                return (partes[0].substring(0, 1) + partes[partes.length - 1].substring(0, 1)).toUpperCase();
            } else {
                return partes[0].substring(0, 1).toUpperCase();
            }
        }
        return "U"; // Usuario por defecto
    }
}