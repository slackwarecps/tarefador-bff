package br.com.fabioalvaro.tarefaFacil.controller.dto;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Pergunta;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.PerguntaStatus;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.PerguntaTipo;

public class DetalhesDaPerguntaDto {
	private Long id;
	private String descricao;
	private PerguntaStatus perguntaStatus;
	private PerguntaTipo perguntaTipo;
	private Boolean obrigatoria;
	private Long peso_pergunta;
	private Boolean tem_acao;
	private String linkVideo;
	private Long quantidade;
	
	
	
	
	
	



	public DetalhesDaPerguntaDto(Pergunta pergunta) {
		
		this.id = pergunta.getId();
		this.descricao = pergunta.getDescricao();
		this.perguntaStatus = pergunta.getPerguntaStatus();
		this.perguntaTipo = pergunta.getPerguntaTipo();
		this.obrigatoria = pergunta.getObrigatoria();
		this.peso_pergunta = pergunta.getPesoPergunta();
		this.tem_acao = pergunta.getTemAcao();
		this.linkVideo = pergunta.getLinkVideo();
		this.quantidade = pergunta.getQuantidade();
	}
	
	public String getLinkVideo() {
		return linkVideo;
	}


	public void setLinkVideo(String linkVideo) {
		this.linkVideo = linkVideo;
	}
	
	
	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public Boolean getObrigatoria() {
		return obrigatoria;
	}


	public Long getPeso_pergunta() {
		return peso_pergunta;
	}


	public Boolean getTem_acao() {
		return tem_acao;
	}


	public Long getId() {
		return id;
	}
	public String getDescricao() {
		return descricao;
	}
	public PerguntaStatus getPerguntaStatus() {
		return perguntaStatus;
	}
	public PerguntaTipo getPerguntaTipo() {
		return perguntaTipo;
	}

	
	
	
	
}
