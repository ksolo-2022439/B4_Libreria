package org.kinscript.b4_libreria.service;

import org.kinscript.b4_libreria.entity.Libro;
import java.util.List;

public interface iLibrosService {
    List<Libro> listarLibros();
    Libro buscarLibro(Integer codigo);
    Libro guardarLibro(Libro libro);
    void eliminarLibro(Libro libro);
}
