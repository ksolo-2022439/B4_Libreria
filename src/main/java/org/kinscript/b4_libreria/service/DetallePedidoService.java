package org.kinscript.b4_libreria.service;

import org.kinscript.b4_libreria.entity.DetallePedido;
import org.kinscript.b4_libreria.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePedidoService implements iDetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Override
    public List<DetallePedido> listarDetallesPedido() {
        return detallePedidoRepository.findAll();
    }

    @Override
    public DetallePedido buscarDetallePedido(Integer idDetallePedido) {
        return detallePedidoRepository.findById(idDetallePedido).orElse(null);
    }

    @Override
    public DetallePedido guardarDetallePedido(DetallePedido detallePedido) {
        return detallePedidoRepository.save(detallePedido);
    }

    @Override
    public void eliminarDetallePedido(DetallePedido detallePedido) {
        detallePedidoRepository.delete(detallePedido);
    }
}