package org.kinscript.b4_libreria.service;

import org.kinscript.b4_libreria.repository.LibrosRepository;
import org.kinscript.b4_libreria.entity.Libro;
import org.kinscript.b4_libreria.repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService implements iLibrosService{

    @Autowired
    private LibrosRepository


    public List<Libro> listarLibros() {
        List<Libro> libros = LibrosRepository.findAll();
        return libros;
    }

    public Libro buscarLibro(Integer codigo) {
        Libro libros = LibrosRepository.findById(codigo).orElse(null);
        return libros;
    }

    public void guardarLibro(Libro libro) {
        LibrosRepository.save(libro);
    }

    public void eliminarLibro(Libro libro) {
        LibrosRepository.delete(libro);
    }
}
