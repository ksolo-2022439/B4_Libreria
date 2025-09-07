package org.kinscript.b4_libreria.service;

import org.kinscript.b4_libreria.repository.LibrosRepository;
import org.kinscript.b4_libreria.entity.Libro;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService implements iLibrosService{

    @Autowired
    private LibrosRepository librosRepository;

    @Override
    public List<Libro> listarLibros() {
        List<Libro> libros = librosRepository.findAll();
        return libros;
    }
    @Override
    public Libro buscarLibro(Integer codigo) {
        Libro libros = librosRepository.findById(codigo).orElse(null);
        return libros;
    }
    @Override
    public Libro guardarLibro(Libro libro) {
        librosRepository.save(libro);
        return libro;
    }
    @Override
    public void eliminarLibro(Libro libro) {

        librosRepository.delete(libro);
    }
}
