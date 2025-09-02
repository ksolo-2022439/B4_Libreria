package org.kinscript.b4_libreria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "DetallePedido")

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetallePedido;
    private Integer cantidad;
    private Double precioUnitario;
    @ManyToOne
    @JoinColumn(name = "idPedido")
    private Pedido pedido;
    @ManyToOne
    @JoinColumn(name = "idLibro")
    private Libro libro;
}
