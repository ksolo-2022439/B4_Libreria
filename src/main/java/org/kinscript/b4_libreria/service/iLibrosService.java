package org.kinscript.b4_libreria.service;

import org.kinscript.b4_libreria.entity.Libro;
import java.util.List;

public interface iLibrosService {
    public List<Libro> listarLibros();
    public Libro buscarLibro(Integer codigo);
    public void guardarLibro(Libro cliente);
    public void eliminarLibro(Libro cliente);
}
