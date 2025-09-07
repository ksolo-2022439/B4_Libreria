package org.kinscript.b4_libreria.repository;

import org.kinscript.b4_libreria.entity.CookieAuth;
import org.kinscript.b4_libreria.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CookieAuthRepository extends JpaRepository<CookieAuth, Integer> {

    Optional<CookieAuth> findByToken(String token);

    @Transactional
    void deleteByUsuario(Usuario usuario);
}