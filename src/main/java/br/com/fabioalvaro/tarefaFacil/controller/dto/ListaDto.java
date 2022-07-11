package br.com.fabioalvaro.tarefaFacil.controller.dto;

import org.springframework.data.domain.Page;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Lista;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Operador;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.StatusLista;

public class ListaDto {
	private Long id;
	private String descricao;
	private String operadorNome;
	private String status;
	
	//recebe uma Lista para construir o DTO
	public ListaDto(Lista lista) {
		
		this.id = lista.getId();
		this.descricao = lista.getDescricao();
		this.operadorNome = lista.getOperador().getNome();
		this.status = lista.getStatus().toString();

	}
	
	// pega um paginador cheio de Objetos e converte em um paginador cheio
	//de DTOs
	public static Page<ListaDto> converter(Page<Lista> listas) {
		// Usando o java 8 faz um mapeamento e depois coleta em uma lista
		return listas.map(ListaDto::new);

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


	public String getOperadorNome() {
		return this.operadorNome;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setOperadorNome(String operadorNome) {
		this.operadorNome = operadorNome;
	}
	
	
	
	
}
