/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.RespostaPossivel;

/**
 * @author faapereira
 *
 */
public interface RespostaPossivelRepository  extends JpaRepository<RespostaPossivel, Long>{
	Page<RespostaPossivel>  findByPerguntaId(Long idPergunta, Pageable paginacao);

	Page<RespostaPossivel>  findByDescricao(String descricao, Pageable paginacao);
	
	List<RespostaPossivel>  findAllByPerguntaId(Long perguntaId);
	
	RespostaPossivel findByPerguntaId(Long id);

	/**
	 * @param id
	 */
	void deleteByPerguntaId(Long id);


}
