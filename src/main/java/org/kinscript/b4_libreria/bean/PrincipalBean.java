package org.kinscript.b4_libreria.bean;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.kinscript.b4_libreria.entity.Libro;
import org.kinscript.b4_libreria.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Component("principalBean")
@RequestScope
@Getter
public class PrincipalBean {

    @Autowired
    private LibroService libroService;

    private List<Libro> librosDestacados;

    @PostConstruct
    public void init() {
        this.librosDestacados = libroService.listarLibros();
    }
}