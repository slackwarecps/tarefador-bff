package br.com.fabioalvaro.tarefaFacil.modelo.tarefas;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class RespostaDada {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Pergunta pergunta;
	@Column(length = 1024)
	private String jsonLinha = "[]";
	@ElementCollection
	@CollectionTable(name = "resposta_dada_lista")
	@Column(length = 1024)
	private List<String> respostaItem = new ArrayList<>();

	public RespostaDada() {

	};

	public RespostaDada(Pergunta pergunta, String jsonLinha, List<String> respostaItem) {

		this.pergunta = pergunta;
		this.jsonLinha = jsonLinha;
		this.respostaItem = respostaItem;
	}

	public String getJsonLinha() {
		return jsonLinha;
	}

	public void setJsonLinha(String jsonLinha) {
		this.jsonLinha = jsonLinha;
	}

	public List<String> getRespostaItem() {
		return respostaItem;
	}

	public void setRespostaItem(List<String> respostaItem) {
		this.respostaItem = respostaItem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pergunta getPergunta() {
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}

	@Override
	public String toString() {
		return "RespostaDada [id=" + id + ", perguntaId=" + pergunta.getId()+ "]";
	}






}
