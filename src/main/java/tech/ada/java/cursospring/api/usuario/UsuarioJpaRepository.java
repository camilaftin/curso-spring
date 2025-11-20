package tech.ada.java.cursospring.api.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<Usuario,Long>{


    Optional<Usuario> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
