package org.kinscript.b4_libreria.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.kinscript.b4_libreria.entity.Pedido;
import org.kinscript.b4_libreria.entity.Usuario;
import org.kinscript.b4_libreria.service.iPedidoService;
import org.kinscript.b4_libreria.service.iUsuarioService;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Component
@ViewScoped
@Data
public class PedidoController implements Serializable {

    @Autowired
    private iPedidoService pedidoService;

    @Autowired
    private iUsuarioService usuarioService;

    private List<Pedido> pedidos;
    private Pedido pedidoSeleccionado;
    private List<Usuario> usuarios;

    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);

    @PostConstruct
    public void init() {
        cargarDatos();
        this.usuarios = usuarioService.listarUsuarios();
    }

    public void cargarDatos() {
        this.pedidos = pedidoService.listarPedidos();
    }

    public void agregarPedido() {
        this.pedidoSeleccionado = new Pedido();
        this.pedidoSeleccionado.setFecha(LocalDate.now());
        this.pedidoSeleccionado.setEstado(Pedido.Estado.CREADO);
    }

    public Pedido.Estado[] getEstados() {
        return Pedido.Estado.values();
    }

    public void guardarPedido() {
        logger.info("Pedido a guardar: " + this.pedidoSeleccionado);

        if (this.pedidoSeleccionado.getIdPedido() == null) {
            pedidoService.guardarPedido(this.pedidoSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pedido Agregado"));
        } else {
            pedidoService.guardarPedido(this.pedidoSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pedido Actualizado"));
        }

        cargarDatos();
        PrimeFaces.current().executeScript("PF('ventanaModalPedido').hide();");
        PrimeFaces.current().ajax().update("formulario-pedidos:tabla-pedidos", "formulario-pedidos:mensaje-emergente");
        this.pedidoSeleccionado = null;
    }

    public void eliminarPedido() {
        logger.info("Pedido a eliminar: " + this.pedidoSeleccionado);
        this.pedidoService.eliminarPedido(this.pedidoSeleccionado);
        this.pedidos.remove(this.pedidoSeleccionado);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Pedido Eliminado"));
        PrimeFaces.current().ajax().update("formulario-pedidos:tabla-pedidos", "formulario-pedidos:mensaje-emergente");
        this.pedidoSeleccionado = null;
    }
}