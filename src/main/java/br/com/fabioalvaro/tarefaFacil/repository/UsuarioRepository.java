package br.com.fabioalvaro.tarefaFacil.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fabioalvaro.tarefaFacil.modelo.forum.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email);

}
