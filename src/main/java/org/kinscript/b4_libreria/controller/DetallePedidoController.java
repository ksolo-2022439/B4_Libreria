package org.kinscript.b4_libreria.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.kinscript.b4_libreria.entity.DetallePedido;
import org.kinscript.b4_libreria.entity.Libro;
import org.kinscript.b4_libreria.entity.Pedido;
import org.kinscript.b4_libreria.service.iDetallePedidoService;
import org.kinscript.b4_libreria.service.iLibrosService;
import org.kinscript.b4_libreria.service.iPedidoService;
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
public class DetallePedidoController implements Serializable {

    @Autowired
    private iDetallePedidoService detallePedidoService;

    @Autowired
    private iPedidoService pedidoService;

    @Autowired
    private iLibrosService libroService;

    private List<DetallePedido> detallesPedido;
    private DetallePedido detallePedidoSeleccionado;

    private List<Pedido> pedidos;
    private List<Libro> libros;

    private static final Logger logger = LoggerFactory.getLogger(DetallePedidoController.class);

    @PostConstruct
    public void init() {
        cargarDatos();
        this.pedidos = pedidoService.listarPedidos();
        this.libros = libroService.listarLibros();
    }

    public void cargarDatos() {
        this.detallesPedido = detallePedidoService.listarDetallesPedido();
    }

    public void agregarDetallePedido() {
        this.detallePedidoSeleccionado = new DetallePedido();
    }

    public void guardarDetallePedido() {
        logger.info("Guardando detalle: " + this.detallePedidoSeleccionado);

        if (this.detallePedidoSeleccionado.getIdDetallePedido() == null) {
            detallePedidoService.guardarDetallePedido(this.detallePedidoSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Detalle de Pedido Agregado"));
        } else {
            detallePedidoService.guardarDetallePedido(this.detallePedidoSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Detalle de Pedido Actualizado"));
        }

        cargarDatos();
        PrimeFaces.current().executeScript("PF('ventanaModalDetalle').hide();");
        PrimeFaces.current().ajax().update("formulario-detalles:tabla-detalles", "formulario-detalles:mensaje-emergente");
        this.detallePedidoSeleccionado = null;
    }

    public void eliminarDetallePedido() {
        logger.info("Eliminando detalle: " + this.detallePedidoSeleccionado);
        this.detallePedidoService.eliminarDetallePedido(this.detallePedidoSeleccionado);
        this.detallesPedido.remove(this.detallePedidoSeleccionado);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Detalle de Pedido Eliminado"));
        PrimeFaces.current().ajax().update("formulario-detalles:tabla-detalles", "formulario-detalles:mensaje-emergente");
        this.detallePedidoSeleccionado = null;
    }
}