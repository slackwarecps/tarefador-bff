/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.dto;

import java.util.List;

/**
 * @author faapereira
 *
 */

public class RespostaLinha {
	private long idPergunta;
	private List<String> respostaLinha;
	public long getIdPergunta() {
		return idPergunta;
	}
	public void setIdPergunta(long idPergunta) {
		this.idPergunta = idPergunta;
	}
	public List<String> getRespostaLinha() {
		return respostaLinha;
	}
	public void setRespostaLinha(List<String> respostaLinha) {
		this.respostaLinha = respostaLinha;
	}
	public RespostaLinha() {
		
	}
	public RespostaLinha(long idPergunta, List<String> respostaLinha) {
		
		this.idPergunta = idPergunta;
		this.respostaLinha = respostaLinha;
	}
	@Override
	public String toString() {
		return "RespostaLinha [idPergunta=" + idPergunta + ", respostaLinha=" + respostaLinha + "]";
	}

	
	



}
