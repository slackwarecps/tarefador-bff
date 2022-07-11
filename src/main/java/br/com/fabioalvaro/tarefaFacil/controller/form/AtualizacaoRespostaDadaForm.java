/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.form;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.RespostaDada;
import br.com.fabioalvaro.tarefaFacil.repository.RespostaDadaRepository;

/**
 * @author faapereira
 *
 */
public class AtualizacaoRespostaDadaForm {
	@NotNull @NotEmpty
	private List<String> respostaLinha;
	
	
	
	
	public RespostaDada atualizar(Long id, RespostaDadaRepository respostaDadaRepository) {	
		RespostaDada respostaDada = respostaDadaRepository.getOne(id);
		respostaDada.setRespostaItem(this.respostaLinha);
		return respostaDada;
		
		
	}




	public List<String> getRespostaLinha() {
		return respostaLinha;
	}




	public void setRespostaLinha(List<String> respostaLinha) {
		this.respostaLinha = respostaLinha;
	}



	
	
	

}
