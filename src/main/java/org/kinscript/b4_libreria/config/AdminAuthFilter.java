package org.kinscript.b4_libreria.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kinscript.b4_libreria.bean.SessionBean;
import org.kinscript.b4_libreria.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(2)
public class AdminAuthFilter implements Filter {

    @Autowired
    private SessionBean sessionBean;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        if (requestURI.contains("/administrador.xhtml")) {

            if (!sessionBean.isLoggedIn() || sessionBean.getUsuarioLogueado().getRol() != Usuario.Rol.ADMINISTRADOR) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/paginaPrincipal.xhtml");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}