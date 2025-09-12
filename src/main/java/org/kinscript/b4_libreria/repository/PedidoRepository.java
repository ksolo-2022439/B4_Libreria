package org.kinscript.b4_libreria.repository;

import org.kinscript.b4_libreria.entity.Pedido;
import org.kinscript.b4_libreria.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByUsuarioOrderByFechaDesc(Usuario usuario);
}