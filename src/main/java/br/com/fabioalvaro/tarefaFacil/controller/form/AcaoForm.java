/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.form;

import java.util.Optional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Acao;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.AcaoTipo;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Lista;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Pergunta;
import br.com.fabioalvaro.tarefaFacil.repository.ListaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.PerguntaRepository;

/**
 * @author faapereira
 *
 */
public class AcaoForm {
	@NotNull
	@Min(1)
	private Long idPergunta;	
	@NotNull
	@NotEmpty
	@Length(min = 3)
	private String descricao;
	@NotNull
	@NotEmpty
	@Length(min = 3)
	private String acaoTipo;
	@NotNull
	@Min(1)@Max(999)
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
	private String prefixoPerguntaDescricao;
	
	
	public String getPrefixoPerguntaDescricao() {
		return prefixoPerguntaDescricao;
	}
	public void setPrefixoPerguntaDescricao(String prefixoPerguntaDescricao) {
		this.prefixoPerguntaDescricao = prefixoPerguntaDescricao;
	}




	
	
	
	
	public Long getIdPergunta() {
		return idPergunta;
	}
	public void setIdPergunta(Long idPergunta) {
		this.idPergunta = idPergunta;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getAcaoTipo() {
		return acaoTipo;
	}
	public void setAcaoTipo(String acaoTipo) {
		this.acaoTipo = acaoTipo;
	}
	public Long getIdListaVinculada() {
		return idListaVinculada;
	}
	public void setIdListaVinculada(Long idListaVinculada) {
		this.idListaVinculada = idListaVinculada;
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

	
	
	/**
	 * @return
	 */
	public Acao converter(ListaRepository listaRepository,PerguntaRepository perguntaRepository) {
		AcaoTipo acaoTipoSelecionada= AcaoTipo.valueOf(acaoTipo);
		
		//busco no banco as FKS se preciso for 
		Optional<Lista> lista = listaRepository.findById(idListaVinculada);
		if (lista.isPresent()) {
			System.out.println("ok achou a lista");
		} else {
			System.out.println("Lista Vinculada - Não Achou!!! NULL");
			return null;
		}
		
		Optional<Pergunta> pergunta = perguntaRepository.findById(idPergunta);
		if (pergunta.isPresent()) {
			System.out.println("ok achou a pergunta");
		} else {
			System.out.println("IdPergunta - NAO ACHOU!!! NULL");
			return null;
		}
		
		//acao tipo
		switch (acaoTipoSelecionada) {
		case ENCAMINHAR_PARA_LISTA:
			acaoTipoSelecionada = AcaoTipo.ENCAMINHAR_PARA_LISTA;
			break;
		case NENHUM:
			acaoTipoSelecionada = AcaoTipo.NENHUM;
			break;
		default:
			acaoTipoSelecionada = AcaoTipo.NENHUM;
		}
		
		return new Acao (descricao, gatilho, gatilhoRegra ,gatilhoValor, lista.get(),pergunta.get(), acaoTipoSelecionada,prefixoPerguntaDescricao);
	}
	
	
}
