package org.kinscript.b4_libreria.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kinscript.b4_libreria.bean.SessionBean;
import org.kinscript.b4_libreria.entity.Usuario;
import org.kinscript.b4_libreria.util.CookieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1) //alta prioridad para que ejecute rapido
public class LandingPageFilter implements Filter {

    @Autowired
    private SessionBean sessionBean;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        if (sessionBean.isLoggedIn()) {

            if (requestURI.endsWith("/iniciarSesion.xhtml") || requestURI.equals(httpRequest.getContextPath() + "/")) {

                String landingPage = CookieHelper.getCookieValue(httpRequest, "user-landing-page");

                if (landingPage != null && !landingPage.isEmpty()) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + landingPage);
                    return;
                } else {
                    if (sessionBean.getUsuarioLogueado().getRol() == Usuario.Rol.ADMINISTRADOR) {
                        httpResponse.sendRedirect(httpRequest.getContextPath() + "/administrador.xhtml");
                    } else {
                        httpResponse.sendRedirect(httpRequest.getContextPath() + "/paginaPrincipal.xhtml");
                    }
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }
}