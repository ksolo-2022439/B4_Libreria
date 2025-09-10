package org.kinscript.b4_libreria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal; // Importar para manejar precios con precisión

@Entity
@Table(name = "Libro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {

    public enum Categoria {
        DRAMA, POESIA, COMEDIA, ACCION, FICCION, ROMANCE, MISTERIO, CIENCIA, HISTORIA, INFANTIL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLibro")
    private Integer idLibro;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "autor")
    private String autor;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private Categoria categoria;

    @Column(name = "urlPortada")
    private String urlPortada;

    @Column(name = "precio")
    private BigDecimal precio;

    @Column(name = "stock")
    private Integer stock;
}