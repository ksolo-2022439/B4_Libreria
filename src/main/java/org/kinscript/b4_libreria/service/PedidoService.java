package org.kinscript.b4_libreria.service;

import org.kinscript.b4_libreria.entity.Pedido;
import org.kinscript.b4_libreria.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService implements iPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido buscarPedido(Integer idPedido) {
        return pedidoRepository.findById(idPedido).orElse(null);
    }

    @Override
    public Pedido guardarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public void eliminarPedido(Pedido pedido) {
        pedidoRepository.delete(pedido);
    }
}