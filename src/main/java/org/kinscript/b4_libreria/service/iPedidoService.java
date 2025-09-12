package org.kinscript.b4_libreria.service;

import org.kinscript.b4_libreria.entity.Pedido;
import org.kinscript.b4_libreria.entity.Usuario;
import java.util.List;

public interface iPedidoService {
    List<Pedido> listarPedidos();
    Pedido buscarPedido(Integer idPedido);
    Pedido guardarPedido(Pedido pedido);
    void eliminarPedido(Pedido pedido);

    List<Pedido> listarPedidosPorUsuario(Usuario usuario);
}