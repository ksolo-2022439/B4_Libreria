package org.kinscript.b4_libreria.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.kinscript.b4_libreria.bean.SessionBean;
import org.kinscript.b4_libreria.entity.Usuario;
import org.kinscript.b4_libreria.service.UsuarioService;
import org.kinscript.b4_libreria.util.CookieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class RememberMeFilter implements Filter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SessionBean sessionBean;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (!sessionBean.isLoggedIn()) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String token = CookieHelper.getCookieValue(httpRequest, "remember-me-token");

            if (token != null && !token.isEmpty()) {
                Optional<Usuario> userOpt = usuarioService.findUserByToken(token);
                userOpt.ifPresent(sessionBean::setUsuarioLogueado);
            }
        }

        chain.doFilter(request, response);
    }
}