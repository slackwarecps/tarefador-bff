/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.RespostaDada;

/**
 * @author faapereira
 *
 */
public class RespostaDadaDto {
	private Long id;
	private List<String> respostaLinha;


	public RespostaDadaDto(RespostaDada respostaDada) {

		this.id = respostaDada.getId();
		this.respostaLinha = respostaDada.getRespostaItem();
	
	}
	


	/*
	 * 2)Conversor Convrte um page em um Dto
	 */
	public static Page<RespostaDadaDto> converter(Page<RespostaDada> redes) {
		// Usando o java 8 faz um mapeamento e depois coleta em uma lista
		return redes.map(RespostaDadaDto::new);

	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}



	public List<String> getRespostaLinha() {
		return respostaLinha;
	}



	public void setRespostaLinha(List<String> respostaLinha) {
		this.respostaLinha = respostaLinha;
	}



	
	
}
