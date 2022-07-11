/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.dto;

import java.util.List;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.RespostaDada;

/**
 * @author faapereira
 *
 */
public class DetalhesDaRespostaDadaDto {
	private Long id;
	private List<String> respostaLinha;
	
	public DetalhesDaRespostaDadaDto() {
	}
	
	public DetalhesDaRespostaDadaDto(RespostaDada respostaDada) {
	
		this.id = respostaDada.getId();
		this.respostaLinha = respostaDada.getRespostaItem();
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
