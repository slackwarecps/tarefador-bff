package br.com.fabioalvaro.tarefaFacil.controller.dto;

import org.springframework.data.domain.Page;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Rede;

public class RedeDto {
	private Long id;
	private String descricao;
	private Boolean ativa=true;

	/*
	 * 1)Construtor Recebe uma rede para contruir o Dto
	 */
	public RedeDto(Rede rede) {

		this.id = rede.getId();
		this.descricao = rede.getDescricao();
		this.ativa = rede.getAtiva();
	}

	/*
	 * 2)Conversor Convrte um page em um Dto
	 */
	public static Page<RedeDto> converter(Page<Rede> redes) {
		// Usando o java 8 faz um mapeamento e depois coleta em uma lista
		return redes.map(RedeDto::new);

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

	public Boolean getAtiva() {
		return ativa;
	}

	public void setAtiva(Boolean ativa) {
		this.ativa = ativa;
	}




}
