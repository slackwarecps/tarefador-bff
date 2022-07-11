package br.com.fabioalvaro.tarefaFacil.controller.dto;

import org.springframework.data.domain.Page;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Loja;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Rede;

public class LojaDto {
	private Long id;
	private String descricao;
	private Rede rede;
	
	//construtor que rede uma loja como parametro
	public LojaDto(Loja loja) {
		
		this.id = loja.getId();
		this.descricao = loja.getDescricao();
		this.rede = loja.getRede();
	}
	
	
	/*
	 * 2)Conversor Convrte um page em um Dto
	 */
	public static Page<LojaDto> converter(Page<Loja> lojas) {
		// Usando o java 8 faz um mapeamento e depois coleta em uma lista
		return lojas.map(LojaDto::new);

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


	public Rede getRede() {
		return rede;
	}


	public void setRede(Rede rede) {
		this.rede = rede;
	}
	
	
	
	
}
