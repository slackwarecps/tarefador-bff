package br.com.fabioalvaro.tarefaFacil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fabioalvaro.tarefaFacil.modelo.forum.Curso;

public interface CursoRepository extends JpaRepository<Curso,Long>{

	Curso findByNome(String nome);
	


}
