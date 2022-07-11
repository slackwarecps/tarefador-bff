/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fabioalvaro.tarefaFacil.modelo.agendamento.Agendamento;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Lista;

/**
 * @author faapereira
 *
 */
public interface AgendamentoRepository extends JpaRepository<Agendamento,Long> {

	/**
	 * @param descricao
	 * @param paginacao
	 * @return
	 */
	Page<Agendamento> findByDescricao(String descricao, Pageable paginacao);

	
	/*
	 * REDE E LOJAS SEM STATUS E SEM OPERADOR
	 */
	@Query(value = "select distinct ag.* from agendamento ag "+
			"	left join agendamento_operadores ao on (ao.agendamento_id =ag.id )  " + 
			"	LEFT JOIN operador  on (ao.operadores_id =operador.id ) 	 " + 
			"	LEFT JOIN loja on (loja.id = operador.loja_id) " + 
			"	where (loja.rede_id in (:filtroDeRede) and loja.id in (:filtroDeLoja) )  ", nativeQuery = true)
	Page<Agendamento> findAllFiltrado(
			@Param("filtroDeRede") List<Integer> filtroDeRede, 
			@Param("filtroDeLoja") List<Integer> filtroDeLoja,
			Pageable paginacao);
	
	
	/*
	 * REDE E LOJAS e COM STATUS SEM OPERADOR
	 */
	@Query(value = "select distinct ag.* from agendamento ag "+
			"	left join agendamento_operadores ao on (ao.agendamento_id =ag.id )  " + 
			"	LEFT JOIN operador  on (ao.operadores_id =operador.id ) 	 " + 
			"	LEFT JOIN loja on (loja.id = operador.loja_id) " + 
			"	where (loja.rede_id in (:filtroDeRede) and loja.id in (:filtroDeLoja) )  " +
			"	 and ag.status in (:filtroDeStatus)", nativeQuery = true)
	Page<Agendamento> findAllFiltradoPorStatus(
			@Param("filtroDeRede") List<Integer> filtroDeRede, 
			@Param("filtroDeLoja") List<Integer> filtroDeLoja,			
			@Param("filtroDeStatus") List<String> filtroDeStatus, 
			Pageable paginacao);
	
	
	/*
	 * REDE E LOJAS SEM STATUS COM OPERADOR
	 */
	@Query(value = "select distinct ag.* from agendamento ag "+
			"	left join agendamento_operadores ao on (ao.agendamento_id =ag.id )  " + 
			"	LEFT JOIN operador  on (ao.operadores_id =operador.id ) 	 " + 
			"	LEFT JOIN loja on (loja.id = operador.loja_id) " + 
			"	where (loja.rede_id in (:filtroDeRede) and loja.id in (:filtroDeLoja) )  " + 
			"	and operador.id in (:filtroDeOperador)  ", nativeQuery = true)
	Page<Agendamento> findAllFiltradoOperador(
			@Param("filtroDeRede") List<Integer> filtroDeRede, 
			@Param("filtroDeLoja") List<Integer> filtroDeLoja,
			@Param("filtroDeOperador") List<Integer> filtroDeOperador,			
			Pageable paginacao);
	
	
	/*
	 * REDE E LOJAS e COM STATUS E COM OPERADOR
	 */
	@Query(value = "select distinct ag.* from agendamento ag "+
			"	left join agendamento_operadores ao on (ao.agendamento_id =ag.id )  " + 
			"	LEFT JOIN operador  on (ao.operadores_id =operador.id ) 	 " + 
			"	LEFT JOIN loja on (loja.id = operador.loja_id) " + 
			"	where (loja.rede_id in (:filtroDeRede) and loja.id in (:filtroDeLoja) )  " + 
			"	and operador.id in (:filtroDeOperador)  " + 
			"	 and ag.status in (:filtroDeStatus)", nativeQuery = true)
	Page<Agendamento> findAllFiltrado(
			@Param("filtroDeRede") List<Integer> filtroDeRede, 
			@Param("filtroDeLoja") List<Integer> filtroDeLoja,
			@Param("filtroDeOperador") List<Integer> filtroDeOperador, 
			@Param("filtroDeStatus") List<String> filtroDeStatus, 
			Pageable paginacao);
	

	 

	
	
}
