package org.kinscript.b4_libreria.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Usuario")

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class Usuario {

    public enum Rol{
        CLIENTE, ADMINISTRADOR
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    private String nombreUsuario;
    private String correoElectronico;
    private String contrasena;
    private Rol rol;
}
