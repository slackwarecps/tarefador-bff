/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Acao;

/**
 * @author faapereira
 *
 */
public interface AcaoRepository extends JpaRepository<Acao, Long> {
	
	Page<Acao>  findByPerguntaId(Long idPergunta, Pageable paginacao);
	
	//Acao findByPerguntaId(Long id);
	
	Optional<Acao> findByPerguntaId(Long id);
	
	
	List<Acao> findAllByPerguntaId(Long id);

	Page<Acao> findByDescricao(String nome, Pageable paginacao);
	
	Long countByPerguntaId(Long perguntaId);

	/**
	 * @param id
	 */
	void deleteByPerguntaId(Long id);
}
