package br.com.fabioalvaro.tarefaFacil.controller.dto;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Rede;

/*
 * Essa classe DTO é utilizada para formatar os Dados que serão exibidos pela chamada REST
 * 
 */

public class DetalhesDaRedeDto {
	//campos
	private Long id;
	private String descricao;
	private Boolean ativa;
	
	/*
	 *  construtor que recebe uma Rede e se preeche com os dados dela
	 */
	public  DetalhesDaRedeDto(Rede rede) {
		this.id = rede.getId();
		this.descricao = rede.getDescricao();
		this.ativa = rede.getAtiva();
	}
	//getters and setters

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

	public Boolean getAtiva() {
		return ativa;
	}

	public void setAtiva(Boolean ativa) {
		this.ativa = ativa;
	}
	
	

	
}
