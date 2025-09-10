package org.kinscript.b4_libreria.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.kinscript.b4_libreria.entity.Libro;
import org.kinscript.b4_libreria.service.iLibrosService;
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
public class LibroController implements Serializable {

    @Autowired
    private iLibrosService libroService;

    private List<Libro> libros;
    private Libro libroSeleccionado;

    private static final Logger logger = LoggerFactory.getLogger(LibroController.class);

    @PostConstruct
    public void init() {
        cargarDatos();
    }

    public void cargarDatos() {
        this.libros = this.libroService.listarLibros();
    }

    public Libro.Categoria[] getCategorias() {
        return Libro.Categoria.values();
    }

    public void agregarLibro() {
        this.libroSeleccionado = new Libro();
    }

    public void guardaLibro() {
        logger.info("Libro a guardar: " + this.libroSeleccionado);
        String mensaje;
        if (this.libroSeleccionado.getIdLibro() == null) {
            this.libroService.guardarLibro(this.libroSeleccionado);
            mensaje = "Libro Agregado";
        } else {
            this.libroService.guardarLibro(this.libroSeleccionado);
            mensaje = "Libro Actualizado";
        }
        cargarDatos();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        PrimeFaces.current().ajax().update("formulario-libros:table-libros", "formulario-libros:mensaje-emergente");
        PrimeFaces.current().executeScript("PF('ventanaModalLibro').hide();");
        this.libroSeleccionado = null;
    }

    public void eliminarLibro() {
        logger.info("Libro a eliminar: " + this.libroSeleccionado);
        this.libroService.eliminarLibro(this.libroSeleccionado);
        this.libros.remove(this.libroSeleccionado);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Libro eliminado"));
        PrimeFaces.current().ajax().update("formulario-libros:tabla-libros", "formulario-libros:mensaje-emergente");
        this.libroSeleccionado = null;
    }
}