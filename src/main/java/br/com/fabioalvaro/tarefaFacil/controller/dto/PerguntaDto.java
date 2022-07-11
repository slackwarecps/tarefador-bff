package br.com.fabioalvaro.tarefaFacil.controller.dto;

import org.springframework.data.domain.Page;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Pergunta;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.PerguntaStatus;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.PerguntaTipo;

/**
 * 
 * @author faapereira classe que "formata" os resultados como um array para a
 *         saida de dados;
 */

public class PerguntaDto {
	private Long id;
	private String descricao;
	private PerguntaStatus perguntaStatus;
	private PerguntaTipo perguntaTipo;
	private Boolean obrigatoria;
	private Long pesoPergunta;
	private Boolean temAcao;
	private String linkVideo;
	private Long quantidade;



	// Construtor recebe um Modelo de parametro para preencher o DTO
	public PerguntaDto(Pergunta pergunta) {

		this.id = pergunta.getId();
		this.descricao = pergunta.getDescricao();
		this.perguntaTipo = pergunta.getPerguntaTipo();
		this.perguntaStatus = pergunta.getPerguntaStatus();
		this.obrigatoria = pergunta.getObrigatoria();
		this.pesoPergunta = pergunta.getPesoPergunta();
		this.temAcao = pergunta.getTemAcao();
		this.linkVideo = pergunta.getLinkVideo();
		this.quantidade = pergunta.getQuantidade();
	}

	// Converte
	public static Page<PerguntaDto> converter(Page<Pergunta> perguntas) {
		// Usando o java 8 faz um mapeamento e depois coleta em uma lista
		return perguntas.map(PerguntaDto::new);
	}
	
	
	


	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public String getLinkVideo() {
		return linkVideo;
	}

	public void setLinkVideo(String linkVideo) {
		this.linkVideo = linkVideo;
	}

	public Boolean getTemAcao() {
		return temAcao;
	}

	public void setTemAcao(Boolean temAcao) {
		this.temAcao = temAcao;
	}

	public Boolean getObrigatoria() {
		return obrigatoria;
	}

	public void setObrigatoria(Boolean obrigatoria) {
		this.obrigatoria = obrigatoria;
	}

	public Long getPesoPergunta() {
		return pesoPergunta;
	}

	public void setPesoPergunta(Long pesoPergunta) {
		this.pesoPergunta = pesoPergunta;
	}
	
	public Long getId() {
		return id;
	}

	public PerguntaTipo getPerguntaTipo() {
		return perguntaTipo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}


	public void setPerguntaTipo(PerguntaTipo perguntaTipo) {
		this.perguntaTipo = perguntaTipo;
	}

	public PerguntaStatus getPerguntaStatus() {
		return perguntaStatus;
	}

	public void setPerguntaStatus(PerguntaStatus perguntaStatus) {
		this.perguntaStatus = perguntaStatus;
	}

}
