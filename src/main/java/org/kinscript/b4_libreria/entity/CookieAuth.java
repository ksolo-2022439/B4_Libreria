package org.kinscript.b4_libreria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "CookieAuth")

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class CookieAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCookie;
    private String token;
    private LocalDate fechaExpiracion;
    @OneToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
}
