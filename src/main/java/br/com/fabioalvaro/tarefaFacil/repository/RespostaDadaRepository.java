/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.RespostaDada;

/**
 * @author faapereira
 *
 */
public interface RespostaDadaRepository  extends JpaRepository<RespostaDada, Long>  {
	Page<RespostaDada> findByPerguntaId(Long idPergunta, Pageable paginacao);
	
	//@Query("select count(*) from RespostaDada R WHERE R.pergunta.id :perguntaId")
	//Long  contarRespostas(@Param("perguntaId") int i);
	
	long countByPerguntaId(Long perguntaId);
	
	RespostaDada findByPerguntaId(Long id);

	/**
	 * @param id
	 */
	void deleteByPerguntaId(Long id);
	

}
