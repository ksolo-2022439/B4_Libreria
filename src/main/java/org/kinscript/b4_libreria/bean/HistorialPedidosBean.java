package org.kinscript.b4_libreria.bean;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.kinscript.b4_libreria.entity.Pedido;
import org.kinscript.b4_libreria.entity.Usuario;
import org.kinscript.b4_libreria.service.iPedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component("historialPedidosBean")
@ViewScoped
@Getter
@Setter
public class HistorialPedidosBean implements Serializable {

    @Autowired
    private iPedidoService pedidoService;

    @Autowired
    private SessionBean sessionBean;

    private List<Pedido> pedidos;
    private List<Pedido> pedidosFiltrados; // Se usará esta lista para mostrar en la vista

    //atributos para los filtros
    private Pedido.Estado filtroEstado;
    private LocalDate filtroDesde;
    private LocalDate filtroHasta;
    private String filtroBusqueda;

    private static final Logger logger = LoggerFactory.getLogger(HistorialPedidosBean.class);

    //cargar pedidos de usuario
    @PostConstruct
    public void init() {
        Usuario usuarioLogueado = sessionBean.getUsuarioLogueado();
        if (usuarioLogueado != null) {
            this.pedidos = pedidoService.listarPedidosPorUsuario(usuarioLogueado);
            this.pedidosFiltrados = this.pedidos; // Inicialmente, mostrar todos
        } else {
            this.pedidos = Collections.emptyList();
            this.pedidosFiltrados = Collections.emptyList();
        }
    }

    //proporciona valores para los filtros
    public Pedido.Estado[] getEstados() {
        return Pedido.Estado.values();
    }

    //aplica los filtros
    public void aplicarFiltros() {
        logger.info("Aplicando filtros: Estado={}, Desde={}, Hasta={}, Búsqueda='{}'",
                filtroEstado, filtroDesde, filtroHasta, filtroBusqueda);

        pedidosFiltrados = pedidos.stream()
                .filter(p -> filtroEstado == null || p.getEstado() == filtroEstado)
                .filter(p -> filtroDesde == null || !p.getFecha().isBefore(filtroDesde))
                .filter(p -> filtroHasta == null || !p.getFecha().isAfter(filtroHasta))
                .filter(p -> filtroBusqueda == null || filtroBusqueda.trim().isEmpty() ||
                        String.valueOf(p.getIdPedido()).contains(filtroBusqueda.trim()) ||
                        p.getDetalles().stream().anyMatch(d -> d.getLibro().getTitulo().toLowerCase().contains(filtroBusqueda.trim().toLowerCase())))
                .collect(Collectors.toList());

        logger.info("Encontrados {} pedidos después de filtrar.", pedidosFiltrados.size());
    }
}