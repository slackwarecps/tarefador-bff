package br.com.fabioalvaro.tarefaFacil.controller.dto;

import org.springframework.data.domain.Page;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Loja;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Operador;

public class OperadorDto {
	private Long id;
	private String nome;
	private Long loja;
	
	//construtor que rede uma loja como parametro
	public OperadorDto(Operador operador) {
		super();
		this.id = operador.getId();
		this.nome = operador.getNome();
		this.loja = operador.getLoja().getId();
	}
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public Long getLoja() {
		return loja;
	}


	public void setLoja(Loja loja) {
		this.loja = loja.getId();
	}


	/*
	 * 2)Conversor Converte um page em um Dto
	 */
	public static Page<OperadorDto> converter(Page<Operador> operadores) {
		// Usando o java 8 faz um mapeamento e depois coleta em uma lista
		return operadores.map(OperadorDto::new);

	}
}
