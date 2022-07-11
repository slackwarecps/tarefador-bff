/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.dto;

import org.springframework.data.domain.Page;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.RespostaPossivel;

/**
 * @author faapereira
 *
 */
public class RespostaPossivelDto {
	private Long id;
	private String descricao;

	// 1 construtor
	public RespostaPossivelDto(RespostaPossivel respostaPossivel) {

		this.id = respostaPossivel.getId();
		this.descricao = respostaPossivel.getDescricao();
	}

	/*
	 * 2)Conversor Converte um page em um Dto
	 */
	public static Page<RespostaPossivelDto> converter(Page<RespostaPossivel> respostaspossiveis) {
		// Usando o java 8 faz um mapeamento e depois coleta em uma lista
		return respostaspossiveis.map(RespostaPossivelDto::new);

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


	
}
