package org.kinscript.b4_libreria.repository;

import org.kinscript.b4_libreria.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}