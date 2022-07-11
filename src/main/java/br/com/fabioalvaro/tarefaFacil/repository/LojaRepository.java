package br.com.fabioalvaro.tarefaFacil.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Loja;

public interface LojaRepository extends JpaRepository<Loja, Long> {

	Page<Loja>  findByRede_Id(Long descricao, Pageable paginacao);
	List<Loja>  findByRede_Id(Long idRede);

	Page<Loja>  findByDescricao(String descricao, Pageable paginacao);

}
