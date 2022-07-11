package br.com.fabioalvaro.tarefaFacil.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Rede;

public interface RedeRepository extends JpaRepository<Rede, Long>  {

	
	
	Page<Rede> findByDescricao(String descricao, Pageable paginacao);
	
	@Query("SELECT t FROM Rede t WHERE t.descricao = :descricao")
	List<Rede> carregarPorDescricao(@Param("descricao") String descricaoPaginacao);

	
}
