package org.kinscript.b4_libreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.kinscript.b4_libreria.entity.Libro;

public interface LibrosRepository extends JpaRepository<Libro, Integer>{

}
