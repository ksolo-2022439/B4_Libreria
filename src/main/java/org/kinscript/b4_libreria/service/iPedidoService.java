package org.kinscript.b4_libreria.service;

import org.kinscript.b4_libreria.entity.Pedido;
import java.util.List;

public interface iPedidoService {
    List<Pedido> listarPedidos();
    Pedido buscarPedido(Integer idPedido);
    Pedido guardarPedido(Pedido pedido);
    void eliminarPedido(Pedido pedido);
}