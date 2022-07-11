/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.RespostaPossivel;
import br.com.fabioalvaro.tarefaFacil.repository.RespostaPossivelRepository;

/**
 * @author faapereira
 *
 */
public class AtualizacaoRespostaPossivelForm {
	@NotNull
	@NotEmpty
	@Length(min = 3)
	private String descricao;
	
	


	public RespostaPossivel atualizar(Long id, RespostaPossivelRepository respostaPossivelRepository) {

		RespostaPossivel respostaPossivel = respostaPossivelRepository.getOne(id);
		respostaPossivel.setDescricao(this.descricao);


		return respostaPossivel;
	}
	

	public String getDescricao() {
		return descricao;
	}




	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



}
