package br.com.fabioalvaro.tarefaFacil.modelo.tarefas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Loja {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	@ManyToOne
	private Rede rede;
	
	public Loja() {
		
	}
	
	public Loja(String descricao, Rede rede) {
		super();
		this.descricao = descricao;
		this.rede = rede;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Rede getRede() {
		return rede;
	}

	public void setRede(Rede rede) {
		this.rede = rede;
	}

	@Override
	public String toString() {
		return "Loja [id=" + id + ", descricao=" + descricao + ", rede=" + rede + "]";
	}
	
	



	
	
	
}
