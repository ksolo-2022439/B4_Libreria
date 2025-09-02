package org.kinscript.b4_libreria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "Pedido")

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class Pedido {

    public enum Estado{
        CREADO, PAGADO, ENVIADO, ENTREGADO, CANCELADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;
    private LocalDate fecha;
    private Estado estado;
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

}
