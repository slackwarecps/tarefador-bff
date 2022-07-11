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

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Lista;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Pergunta;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.PerguntaStatus;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.PerguntaTipo;
import br.com.fabioalvaro.tarefaFacil.repository.ListaRepository;

/**
 * @author faapereira
 *
 */
public class PerguntaForm {
	@NotNull @NotEmpty @Length(min=2)
	private String descricao;
	@NotNull
	private Boolean obrigatoria;
	@NotNull
	@Min(1)@Max(999)
	private Long pesoPergunta;
	@NotNull
	@Min(1)
	private Long idLista;
	@NotNull @NotEmpty @Length(min=5)
	private String tipo;
	private String linkVideo;
	private Long quantidade;






	// 
	public Pergunta converter(ListaRepository listaRepository) {
		System.out.println("vou converter...");
		PerguntaTipo perguntaTipoSelecionada= PerguntaTipo.valueOf(tipo);
			
		switch (perguntaTipoSelecionada) {
		case ESCOLHA_SIMPLES:
			perguntaTipoSelecionada = PerguntaTipo.ESCOLHA_SIMPLES;
			break;
		case ESCOLHA_MULTIPLA:
			perguntaTipoSelecionada = PerguntaTipo.ESCOLHA_MULTIPLA;
			break;
		case NUMERO_INTEIRO:
			perguntaTipoSelecionada = PerguntaTipo.NUMERO_INTEIRO;
			break;
		case NUMERO_DECIMAL:
			perguntaTipoSelecionada = PerguntaTipo.NUMERO_DECIMAL;
			break;
		case BOOLEAN:
			perguntaTipoSelecionada = PerguntaTipo.BOOLEAN;
			break;
		case FOTO_UNICA:
			perguntaTipoSelecionada = PerguntaTipo.FOTO_UNICA;
			break;
		case FOTO_MULTIPLAS:
			perguntaTipoSelecionada = PerguntaTipo.FOTO_MULTIPLAS;
			break;
		case TEXTO_LIVRE:
			perguntaTipoSelecionada = PerguntaTipo.TEXTO_LIVRE;
			break;
		case TREINAMENTO:
			perguntaTipoSelecionada = PerguntaTipo.TREINAMENTO;
			break;	
		default:
			perguntaTipoSelecionada = PerguntaTipo.TEXTO_LIVRE;
		}
		
		//busca a lista
		Optional<Lista> lista = listaRepository.findById(idLista);	
		if (lista.isPresent()) {
			return new Pergunta(descricao, obrigatoria, pesoPergunta, lista.get(),
					PerguntaStatus.NAO_RESPONDIDA, perguntaTipoSelecionada,linkVideo,quantidade);

		} else {
			System.out.println("Não Existe essa Lista "+idLista+" retornou null!!");
			return null;
		}
	
	}


	
	public String getLinkVideo() {
		return linkVideo;
	}




	public void setLinkVideo(String linkVideo) {
		this.linkVideo = linkVideo;
	}


	public String getDescricao() {
		return descricao;
	}




	public void setDescricao(String descricao) {
		this.descricao = descricao;
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




	public Long getIdLista() {
		return idLista;
	}




	public void setIdLista(Long idLista) {
		this.idLista = idLista;
	}




	public String getTipo() {
		return tipo;
	}




	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	@Override
	public String toString() {
		return "PerguntaForm [descricao=" + descricao + ", obrigatoria=" + obrigatoria + ", pesoPergunta="
				+ pesoPergunta + ", idLista=" + idLista + ", tipo=" + tipo + ", quantidade=" + quantidade + ", linkVideo=" + linkVideo + "]";
	}



	public Long getQuantidade() {
		return quantidade;
	}



	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}




	
	
}
