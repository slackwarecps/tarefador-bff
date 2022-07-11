package br.com.fabioalvaro.tarefaFacil.modelo.tarefas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemResposta {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private RespostaDada respostaDada;
	private String resposta;

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

}
