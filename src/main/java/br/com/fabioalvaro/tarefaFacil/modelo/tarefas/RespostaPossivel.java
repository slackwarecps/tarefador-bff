package br.com.fabioalvaro.tarefaFacil.modelo.tarefas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class RespostaPossivel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	@ManyToOne
	private Pergunta pergunta;	
	private String descricao;
	
	
	public RespostaPossivel() {
		
	}
	
	
	public RespostaPossivel(String descricao,Pergunta pergunta) {

		this.pergunta = pergunta;
		this.descricao = descricao;
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	@Override
	public String toString() {
		return "RespostaPossivel [id=" + id + ", pergunta=" + pergunta.getId() + ", descricao=" + descricao + "]";
	}
	
	

	

	
	
}
