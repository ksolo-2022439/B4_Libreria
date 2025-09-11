package org.kinscript.b4_libreria.service;

import org.kinscript.b4_libreria.entity.*;
import org.kinscript.b4_libreria.repository.DetallePedidoRepository;
import org.kinscript.b4_libreria.repository.LibrosRepository;
import org.kinscript.b4_libreria.repository.PedidoRepository;
import org.kinscript.b4_libreria.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
public class CompraService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private LibrosRepository libroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Transactional
    public void procesarCompra(HttpSession session, Integer usuarioId) throws Exception {
        Carrito carrito = carritoService.getCarrito(session);
        if (carrito.getItems().isEmpty()) {
            throw new IllegalStateException("El carrito de compras está vacío.");
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }
        Usuario usuario = usuarioOpt.get();

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setFecha(LocalDate.now());
        pedido.setEstado(Pedido.Estado.PAGADO);

        pedidoRepository.save(pedido);

        for (Map.Entry<Integer, Integer> item : carrito.getItems().entrySet()) {
            Integer libroId = item.getKey();
            Integer cantidad = item.getValue();

            Optional<Libro> libroOpt = libroRepository.findById(libroId);
            if (libroOpt.isEmpty()) {
                throw new Exception("Libro no encontrado con ID: " + libroId);
            }
            Libro libro = libroOpt.get();

            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setPedido(pedido);
            detallePedido.setLibro(libro);
            detallePedido.setCantidad(cantidad);
            detallePedido.setPrecioUnitario(libro.getPrecio().doubleValue());

            detallePedidoRepository.save(detallePedido);
        }

        carritoService.vaciarCarrito(session);
    }
}