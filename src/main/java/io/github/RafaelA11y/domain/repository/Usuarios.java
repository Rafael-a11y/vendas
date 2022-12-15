package io.github.RafaelA11y.domain.repository;

import io.github.RafaelA11y.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Usuarios extends JpaRepository<Usuario, Integer>
{
    Optional<Usuario> findByLoguin(String loguin);
}
