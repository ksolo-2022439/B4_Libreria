package org.kinscript.b4_libreria.service;

import org.kinscript.b4_libreria.entity.DetallePedido;
import java.util.List;

public interface iDetallePedidoService {
    List<DetallePedido> listarDetallesPedido();
    DetallePedido buscarDetallePedido(Integer idDetallePedido);
    DetallePedido guardarDetallePedido(DetallePedido detallePedido);
    void eliminarDetallePedido(DetallePedido detallePedido);
}