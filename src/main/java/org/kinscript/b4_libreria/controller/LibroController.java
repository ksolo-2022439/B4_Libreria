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

    private static Logger logger = LoggerFactory.getLogger(LibroController.class);

    @PostConstruct
    public void init() {
        cargarDatos();
    }

    public void cargarDatos() {
        this.libros = this.libroService.listarLibros();
        this.libros.forEach(libro -> logger.info(libro.toString()));
    }

    // Preparar un nuevo libro
    public void agregarLibro() {
        this.libroSeleccionado = new Libro();
    }

    // Guardar o actualizar libro
    public void guardaLibro() {
        logger.info("Libro a guardar: " + this.libroSeleccionado);

        if (this.libroSeleccionado.getIdLibro() == null) {
            Libro nuevo = this.libroService.guardarLibro(this.libroSeleccionado);
            this.libros.add(nuevo); // Agregar a la lista para actualizar la tabla
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Libro agregado"));
        } else {
            this.libroService.guardarLibro(this.libroSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Libro actualizado"));
        }
        cargarDatos();

        PrimeFaces.current().executeScript("PF('ventanaModalLibro').hide();");

        PrimeFaces.current().ajax().update("formulario-libros:table-libros", "formulario-libros:mensaje-emergente");

        this.libroSeleccionado = null;
    }

    // Eliminar libro
    public void eliminarLibro() {
        logger.info("Libro a eliminar: " + this.libroSeleccionado);
        this.libroService.eliminarLibro(this.libroSeleccionado);
        this.libros.remove(this.libroSeleccionado);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Libro eliminado"));
        PrimeFaces.current().ajax().update("formulario-libros:table-libros", "formulario-libros:mensaje-emergente");

        this.libroSeleccionado = null;
    }
}
