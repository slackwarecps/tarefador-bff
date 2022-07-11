package br.com.fabioalvaro.tarefaFacil.modelo.tarefas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Operador {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String nome;
	@ManyToOne
	private Loja loja;
	private Long idUsuario=null;
	
	


	//1)construtor Padrao
	public Operador() {
		
	}
	
	//2)Construtor a partir de valores
	public Operador(String nome, Loja loja,Long idUsuario) {

	
		this.nome = nome;
		this.loja = loja;
		this.idUsuario = idUsuario;
	}
	
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Loja getLoja() {
		return loja;
	}
	public void setLoja(Loja loja) {
		this.loja = loja;
	}
	
	
	
	

}
