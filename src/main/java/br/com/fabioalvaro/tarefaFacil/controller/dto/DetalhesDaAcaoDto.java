/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.dto;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Acao;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.AcaoTipo;

/**
 * @author faapereira
 *
 */
public class DetalhesDaAcaoDto {
	private Long id;
	private String descricao;
	private String gatilho;
	private String gatilhoRegra;
	private String gatilhoValor;
	private AcaoTipo AcaoTipo;
	private Long idPergunta;
	private Long idListaVinculada;
	
	
	/*
	 * 1)Construtor Recebe uma rede para contruir o Dto
	 */
	public DetalhesDaAcaoDto(Acao acao) {

		this.id = acao.getId();
		this.descricao = acao.getDescricao();
		this.gatilho = acao.getGatilho();
		this.gatilhoRegra = acao.getGatilhoRegra();
		this.gatilhoValor = acao.getGatilhoValor();
		this.AcaoTipo = acao.getAcaoTipo();
		this.idPergunta = acao.getPergunta().getId();
		this.idListaVinculada = acao.getListaVinculada().getId();
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


	public String getGatilho() {
		return gatilho;
	}


	public void setGatilho(String gatilho) {
		this.gatilho = gatilho;
	}


	public String getGatilhoRegra() {
		return gatilhoRegra;
	}


	public void setGatilhoRegra(String gatilhoRegra) {
		this.gatilhoRegra = gatilhoRegra;
	}


	public String getGatilhoValor() {
		return gatilhoValor;
	}


	public void setGatilhoValor(String gatilhoValor) {
		this.gatilhoValor = gatilhoValor;
	}


	public AcaoTipo getAcaoTipo() {
		return AcaoTipo;
	}


	public void setAcaoTipo(AcaoTipo acaoTipo) {
		AcaoTipo = acaoTipo;
	}


	public Long getIdPergunta() {
		return idPergunta;
	}


	public void setIdPergunta(Long idPergunta) {
		this.idPergunta = idPergunta;
	}


	public Long getIdListaVinculada() {
		return idListaVinculada;
	}


	public void setIdListaVinculada(Long idListaVinculada) {
		this.idListaVinculada = idListaVinculada;
	}
	
	
	
	
}
