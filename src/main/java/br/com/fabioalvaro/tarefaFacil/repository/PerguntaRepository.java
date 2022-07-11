package br.com.fabioalvaro.tarefaFacil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Pergunta;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long>  {
	
	Page<Pergunta> findByListaId(Long idlista, Pageable paginacao);
	
	Page<Pergunta> findByDescricao(String descricao, Pageable paginacao);
	
	Pergunta findByDescricao(String nome);
	
	
}
