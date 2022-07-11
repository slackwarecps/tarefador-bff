/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Acao;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.AcaoTipo;
import br.com.fabioalvaro.tarefaFacil.repository.AcaoRepository;

/**
 * @author faapereira
 *
 */
public class AtualizacaoAcaoForm {
	@NotNull
	@Min(1)
	private Long idPergunta;	
	@NotNull
	@NotEmpty
	@Length(min = 3)
	private String descricao;
	@NotNull
	private AcaoTipo acaoTipo;
	@NotNull
	@Min(1)
	private Long idListaVinculada;
	@NotNull
	@NotEmpty
	@Length(min = 3)
	private String gatilho;
	@NotNull
	@NotEmpty
	@Length(min = 1)
	private String gatilhoRegra;
	@NotNull
	@NotEmpty
	@Length(min = 1)
	private String gatilhoValor;
	
	/**
	 * @param id
	 * @param acaoRepository
	 * @return
	 */
	public Acao atualizar(Long id, AcaoRepository acaoRepository) {
		Acao acao = acaoRepository.getOne(id);
		acao.setDescricao(this.descricao);
		acao.setGatilho(gatilho);
		acao.setGatilhoRegra(gatilhoRegra);
		acao.setGatilhoValor(gatilhoValor);
		acao.setAcaoTipo(acaoTipo);
		
		
		return acao;
	}

	public Long getIdPergunta() {
		return idPergunta;
	}

	public String getDescricao() {
		return descricao;
	}



	public AcaoTipo getAcaoTipo() {
		return acaoTipo;
	}

	public void setAcaoTipo(AcaoTipo acaoTipo) {
		this.acaoTipo = acaoTipo;
	}

	public void setIdPergunta(Long idPergunta) {
		this.idPergunta = idPergunta;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setIdListaVinculada(Long idListaVinculada) {
		this.idListaVinculada = idListaVinculada;
	}

	public void setGatilho(String gatilho) {
		this.gatilho = gatilho;
	}

	public void setGatilhoRegra(String gatilhoRegra) {
		this.gatilhoRegra = gatilhoRegra;
	}

	public void setGatilhoValor(String gatilhoValor) {
		this.gatilhoValor = gatilhoValor;
	}

	public Long getIdListaVinculada() {
		return idListaVinculada;
	}

	public String getGatilho() {
		return gatilho;
	}

	public String getGatilhoRegra() {
		return gatilhoRegra;
	}

	public String getGatilhoValor() {
		return gatilhoValor;
	}

	
	
}
