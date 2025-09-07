package org.kinscript.b4_libreria.bean;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.kinscript.b4_libreria.entity.Usuario;
import org.kinscript.b4_libreria.service.UsuarioService;
import org.kinscript.b4_libreria.util.CookieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component("loginBean")
@RequestScope
@Getter
@Setter
public class LoginBean {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SessionBean sessionBean;

    private String correoElectronico;
    private String contrasena;
    private boolean rememberMe;

    public String iniciarSesion() {
        try {
            Usuario usuario = usuarioService.login(correoElectronico, contrasena);
            sessionBean.setUsuarioLogueado(usuario);

            String redirectPage;
            if (usuario.getRol() == Usuario.Rol.ADMINISTRADOR) {
                redirectPage = "/administrador.xhtml";
            } else {
                redirectPage = "/paginaPrincipal.xhtml";
            }

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            int maxAge = 30 * 24 * 60 * 60;

            if (rememberMe) {
                String token = usuarioService.createRememberMeToken(usuario);
                CookieHelper.addCookie(response, "remember-me-token", token, maxAge);
            }

            CookieHelper.addCookie(response, "user-landing-page", redirectPage, maxAge);

            return redirectPage + "?faces-redirect=true";

        } catch (SecurityException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return null;
        }
    }
}