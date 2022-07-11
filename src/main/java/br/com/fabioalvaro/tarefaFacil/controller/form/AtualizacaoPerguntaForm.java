/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Pergunta;
import br.com.fabioalvaro.tarefaFacil.repository.PerguntaRepository;

/**
 * @author faapereira
 *
 */
public class AtualizacaoPerguntaForm {
	private String descricao;
	private Boolean obrigatoria;
	private Long pesoPergunta;
	private String linkVideo;
	private Long quantidade;


	public Pergunta atualizar(Long id, PerguntaRepository perguntaRepository) {
		Pergunta pergunta = perguntaRepository.getOne(id);
		pergunta.setDescricao(this.descricao);
		pergunta.setObrigatoria(this.obrigatoria);
		pergunta.setPesoPergunta(this.pesoPergunta);
		pergunta.setLinkVideo(this.linkVideo);
		pergunta.setQuantidade(this.quantidade);
		
		
		return pergunta;
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



	
	

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
	
}
