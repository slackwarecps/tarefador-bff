/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.dto;

import org.springframework.data.domain.Page;

import br.com.fabioalvaro.tarefaFacil.modelo.agendamento.Agendamento;

/**
 * @author faapereira
 *
 */
public class AgendamentoDto {
	private Long id;
	private String descricao;
	
	
	
	
	
	public AgendamentoDto(Agendamento agendamento) {
		super();
		this.id = agendamento.getId();
		this.descricao = agendamento.getDescricao();
	}
	

	/*
	 * 2)Conversor Convrte um page em um Dto
	 */
	public static Page<AgendamentoDto> converter(Page<Agendamento> agendamentos) {
		// Usando o java 8 faz um mapeamento e depois coleta em uma lista
		return agendamentos.map(AgendamentoDto::new);

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
