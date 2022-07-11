package br.com.fabioalvaro.tarefaFacil.modelo.tarefas;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Lista {	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	@ManyToOne
	private Operador operador;
	@Enumerated(EnumType.STRING)
	private StatusLista status = StatusLista.ATIVA;
	
	
	public Lista() {
	
	}


	public Lista(String descricao, Operador operador,StatusLista status) {
		super();
		this.descricao = descricao;
		this.operador = operador;
		this.status = status;
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


	public StatusLista getStatus() {
		return status;
	}


	public void setStatus(StatusLista status) {
		this.status = status;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Operador getOperador() {
		return operador;
	}


	public void setOperador(Operador operador) {
		this.operador = operador;
	}


	@Override
	public String toString() {
		return "Lista [id=" + id + ", descricao=" + descricao + ", status=" + status +", operador=" + operador.getNome() + "]";
	}
	
	
	
	
}
