package org.kinscript.b4_libreria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "Pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Pedido {

    public enum Estado {
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

    //relación para poder acceder a los detalles desde el pedido
    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DetallePedido> detalles;

    // metodo para retornar el total monetario del pedido
    public double getTotal() {
        if (detalles == null || detalles.isEmpty()) {
            return 0.0;
        }
        return detalles.stream()
                .mapToDouble(detalle -> detalle.getCantidad() * detalle.getPrecioUnitario())
                .sum();
    }
}