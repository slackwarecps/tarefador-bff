package br.com.fabioalvaro.tarefaFacil.controller.dto;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Lista;

/*
 * Essa classe DTO é utilizada para formatar os Dados que serão exibidos pela chamada REST
 * @author Fabio Alvaro Pereira
 * 
 */

public class DetalhesDaListaDto {
	private Long id;
	private String descricao;
	private Long idOperador;
	private String status;



	// preenche o dto baseado no objeto recebido
	public DetalhesDaListaDto(Lista lista) {

		this.id = lista.getId();
		this.descricao = lista.getDescricao();
		this.idOperador = lista.getOperador().getId();
		this.status = lista.getStatus().toString();
	}



	public Long getId() {
		return id;
	}



	public String getDescricao() {
		return descricao;
	}



	public Long getIdOperador() {
		return idOperador;
	}



	public String getStatus() {
		return status;
	}



}
