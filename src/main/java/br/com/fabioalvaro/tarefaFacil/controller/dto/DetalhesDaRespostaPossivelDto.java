/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.dto;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Pergunta;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.RespostaPossivel;

/**
 * @author faapereira
 *
 */
public class DetalhesDaRespostaPossivelDto {
	private Long id;
	private String descricao;
	private Long idPergunta;

	/*
	 * construtor que recebe uma Rede e se preeche com os dados dela
	 */
	public  DetalhesDaRespostaPossivelDto(RespostaPossivel respostaPossivel) {
		this.id = respostaPossivel.getId();
		this.descricao = respostaPossivel.getDescricao();
		this.idPergunta = respostaPossivel.getPergunta().getId();
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	

}
