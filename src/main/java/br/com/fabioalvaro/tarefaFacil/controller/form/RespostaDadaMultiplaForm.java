/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.form;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.google.gson.Gson;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Pergunta;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.RespostaDada;
import br.com.fabioalvaro.tarefaFacil.repository.PerguntaRepository;

/**
 * @author faapereira
 *
 */
public class RespostaDadaMultiplaForm {
	@NotNull
	@Min(1)
	private Long idPergunta;
	private List<String> respostaLinha;

	public RespostaDada converter(PerguntaRepository perguntaRepository) {
		System.out.println("chegou no converter");
		System.out.println("Quantidade de Respostas na Linha:" + respostaLinha.size());

		// Buscando o Foreign
		Optional<Pergunta> pergunta = perguntaRepository.findById(idPergunta);
		if (pergunta.isPresent()) {			
			String respostaLinhaAsJson = new Gson().toJson(respostaLinha);
			System.out.println(respostaLinhaAsJson);
			System.out.println("JSON ARRAY");
			//copia o Json
			String conteudoResposta = respostaLinhaAsJson;
		
			return new RespostaDada(pergunta.get(),respostaLinhaAsJson,respostaLinha);

		} else {
			System.out.println("retornou null!!");
			return null;
		}

	}

	public Long getIdPergunta() {
		return idPergunta;
	}

	public void setIdPergunta(Long idPergunta) {
		this.idPergunta = idPergunta;
	}

	public List<String> getRespostaLinha() {
		return respostaLinha;
	}

	public void setRespostaLinha(List<String> respostaLinha) {
		this.respostaLinha = respostaLinha;
	}
}
