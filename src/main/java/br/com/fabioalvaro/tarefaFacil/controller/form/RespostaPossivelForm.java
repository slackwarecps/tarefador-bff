/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.form;

import java.util.Optional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Pergunta;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.RespostaPossivel;
import br.com.fabioalvaro.tarefaFacil.repository.PerguntaRepository;

/**
 * @author faapereira
 *
 */
public class RespostaPossivelForm {
	@NotNull	@NotEmpty 	@Length(min = 3)
	private String descricao;
	@NotNull
	@Min(1)
	private Long idPergunta;
	
	



	public String getDescricao() {
		return descricao;
	}





	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}





	public Long getIdPergunta() {
		return idPergunta;
	}





	public void setIdPergunta(Long idPergunta) {
		this.idPergunta = idPergunta;
	}





	public RespostaPossivel converter(PerguntaRepository perguntaRepository) {
		//Buscando o Foreign 
		Optional<Pergunta> pergunta = perguntaRepository.findById(idPergunta);
		if (pergunta.isPresent()) {
			return new RespostaPossivel (descricao,pergunta.get());
			
		}else {
			System.out.println("retornou null!!");
			return null;
		}
	}

}
