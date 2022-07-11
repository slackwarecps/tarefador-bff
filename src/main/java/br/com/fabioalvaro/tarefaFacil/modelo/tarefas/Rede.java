package br.com.fabioalvaro.tarefaFacil.modelo.tarefas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Rede {
	//propriedades
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)     
	private Long id;
	private String descricao;
	private Boolean ativa;
	//construtor padrao
	public Rede() {
		
	}
		
	//construtor nomeado
	public Rede(String descricao,Boolean ativa) {
		this.descricao = descricao;	
		if (ativa == null)
			this.ativa = true;
		else
			this.ativa = ativa;	
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



	@Override
	public String toString() {
		return "Rede [id=" + id + ", descricao=" + descricao + ", ativa=" + ativa + "]";
	}

	
	
	

}
