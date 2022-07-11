package br.com.fabioalvaro.tarefaFacil.modelo.tarefas;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Pergunta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private Boolean obrigatoria;
	private Long pesoPergunta=(long)0;
	private Boolean temAcao =false;
	@ManyToOne
	private Lista lista;
	@Enumerated(EnumType.STRING)
	private PerguntaStatus perguntaStatus;
	@Enumerated(EnumType.STRING)
	private PerguntaTipo perguntaTipo;
	@OneToMany(mappedBy = "pergunta")
	private List<RespostaDada> respostasDadas = new ArrayList<>();
	@OneToMany(mappedBy = "pergunta")
	private List<RespostaPossivel> respostasPossiveis = new ArrayList<>();
	private String linkVideo=""; 
	private Long quantidade;
	

	public Pergunta() {
		
	}

	public Pergunta(String descricao, Boolean obrigatoria, Long pesoPergunta, Lista lista,
			PerguntaStatus perguntaStatus, PerguntaTipo perguntaTipo,String linkVideo,Long quantidade) {

		this.descricao = descricao;
		this.obrigatoria = obrigatoria;

		this.pesoPergunta = pesoPergunta;
		this.lista = lista;
		this.perguntaStatus = perguntaStatus;
		this.perguntaTipo = perguntaTipo;
		this.linkVideo = linkVideo;
		this.quantidade = quantidade;
	
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


	public void setId(Long id) {
		this.id = id;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public void setObrigatoria(Boolean obrigatoria) {
		this.obrigatoria = obrigatoria;
	}


	public void setPesoPergunta(Long pesoPergunta) {
		this.pesoPergunta = pesoPergunta;
	}


	public void setLista(Lista lista) {
		this.lista = lista;
	}


	public void setPerguntaStatus(PerguntaStatus perguntaStatus) {
		this.perguntaStatus = perguntaStatus;
	}


	public void setPerguntaTipo(PerguntaTipo perguntaTipo) {
		this.perguntaTipo = perguntaTipo;
	}


	public void setRespostasDadas(List<RespostaDada> respostasDadas) {
		this.respostasDadas = respostasDadas;
	}


	public void setRespostasPossiveis(List<RespostaPossivel> respostasPossiveis) {
		this.respostasPossiveis = respostasPossiveis;
	}




	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public Boolean getObrigatoria() {
		return obrigatoria;
	}

	public Long getPesoPergunta() {
		return pesoPergunta;
	}

	public Lista getLista() {
		return lista;
	}

	public PerguntaStatus getPerguntaStatus() {
		return perguntaStatus;
	}

	public PerguntaTipo getPerguntaTipo() {
		return perguntaTipo;
	}

	public List<RespostaDada> getRespostasDadas() {
		return respostasDadas;
	}

	public List<RespostaPossivel> getRespostasPossiveis() {
		return respostasPossiveis;
	}



	@Override
	public String toString() {
		return "Pergunta [id=" + id + ", descricao=" + descricao + ", obrigatoria=" + obrigatoria + ", pesoPergunta="
				+ pesoPergunta + ", temAcao=" + temAcao + ", lista=" + lista + ", perguntaStatus=" + perguntaStatus
				+ ", perguntaTipo=" + perguntaTipo + ", respostasDadas=" + respostasDadas.size() + ", respostasPossiveis="
				+ respostasPossiveis.size() + ", linkVideo=" + linkVideo + "]";
	}


	
	
	

}
