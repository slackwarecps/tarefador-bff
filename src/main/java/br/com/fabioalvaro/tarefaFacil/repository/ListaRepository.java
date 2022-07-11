package br.com.fabioalvaro.tarefaFacil.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Lista;

public interface ListaRepository extends JpaRepository<Lista, Long> {
	
	
	
	Page<Lista> findByOperadorId(Long id, Pageable paginacao);

	Page<Lista> findByDescricao(String nome, Pageable paginacao);

	/*
	 * SELECT * from Lista lista left join Operador operador on (lista.operador_id
	 * =operador.id ) left join Loja loja on (operador.loja_id =loja.id ) where
	 * (loja.rede_id in (1) )
	 */
	@Query(value = "SELECT * from Lista lista LEFT JOIN operador on lista.operador_id= operador.id 	LEFT JOIN loja on loja.id = operador.loja_id 	WHERE loja.rede_id in (:filtro)", nativeQuery = true)
	Page<Lista> findAllFiltradoAntigo(@Param("filtro") List<Integer> filtro, Pageable paginacao);
	
	
	/*
	 * REDE E LOJAS SEM STATUS E SEM OPERADOR
	 */
	@Query(value = "SELECT * from lista lista LEFT JOIN operador on lista.operador_id= operador.id 	LEFT JOIN loja on loja.id = operador.loja_id 	"
			+ "WHERE (loja.rede_id in (:filtroRede) and loja.id in (:filtroLoja) )", nativeQuery = true)
	Page<Lista> findAllFiltrado(@Param("filtroRede") List<Integer> filtroRede,
								 @Param("filtroLoja") List<Integer> filtroLoja,
								 Pageable paginacao);
	
	
	/*
	 * REDE E LOJAS e COM STATUS SEM OPERADOR
	 */
	@Query(value = "SELECT * from lista lista LEFT JOIN operador on lista.operador_id= operador.id 	LEFT JOIN loja on loja.id = operador.loja_id 	"
			+ "WHERE (loja.rede_id in (:filtroRede) and loja.id in (:filtroLoja) ) and lista.status in (:filtroStatus)", nativeQuery = true)
	Page<Lista> findAllFiltradoPorStatus(@Param("filtroRede") List<Integer> filtroRede,
								 @Param("filtroLoja") List<Integer> filtroLoja,
								 @Param("filtroStatus") List<String> filtroStatus,
								 Pageable paginacao);
	
	
	/*
	 * REDE E LOJAS SEM STATUS COM OPERADOR
	 */
	@Query(value = "SELECT * from lista lista LEFT JOIN operador on lista.operador_id= operador.id 	LEFT JOIN loja on loja.id = operador.loja_id 	"
			+ "WHERE (loja.rede_id in (:filtroRede) and loja.id in (:filtroLoja) ) and lista.operador_id in (:filtroOperador)", nativeQuery = true)
	Page<Lista> findAllFiltradoOperador(@Param("filtroRede") List<Integer> filtroRede,
								 @Param("filtroLoja") List<Integer> filtroLoja,
								 @Param("filtroOperador") List<Integer> filtroOperador,
								 Pageable paginacao);
	
	
	
	/*
	 * REDE E LOJAS e COM STATUS E COM OPERADOR
	 */
	@Query(value = "SELECT * from lista lista LEFT JOIN operador on lista.operador_id= operador.id 	LEFT JOIN loja on loja.id = operador.loja_id 	"
			+ "WHERE (loja.rede_id in (:filtroRede) and loja.id in (:filtroLoja) ) and lista.operador_id in (:filtroOperador) and lista.status in (:filtroStatus)", nativeQuery = true)
	Page<Lista> findAllFiltrado(@Param("filtroRede") List<Integer> filtroRede,
								 @Param("filtroLoja") List<Integer> filtroLoja,
								 @Param("filtroOperador") List<Integer> filtroOperador,
								 @Param("filtroStatus") List<String> filtroStatus,
								 Pageable paginacao);

	/**
	 * @param id
	 * @return
	 */
	
	Long countByOperador_Id(long idOperador);

	

	
	

	
	
	
}
