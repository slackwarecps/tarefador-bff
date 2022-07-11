package br.com.fabioalvaro.tarefaFacil.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Operador;

public interface OperadorRepository extends JpaRepository<Operador, Long>{
	Page<Operador>  findByLoja_Id(Long descricao, Pageable paginacao);

	Page<Operador>  findByNome(String nome, Pageable paginacao);

	/**
	 * @param listaOperadores
	 * @return
	 */
	
	 @Query(value = "select count(id) as contador from operador WHERE id in (:listaOperador)",nativeQuery = true)
	 Integer countById(@Param("listaOperador") List<Long> listaOperador);


	
	/*
	 * REDE E LOJAS SEM STATUS E SEM OPERADOR
	 */
	@Query(value = "SELECT * FROM operador op left join loja on (loja.id = op.loja_id) WHERE loja.id in(:filtroDeLoja) and loja.rede_id in (:filtroDeRede) ", nativeQuery = true)
	Page<Operador> findAllFiltrado(@Param("filtroDeRede") List<Integer> filtroDeRede,
								 @Param("filtroDeLoja") List<Integer> filtroDeLoja,
								 Pageable paginacao);
	
	
	
	
}
