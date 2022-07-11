/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.form;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.google.gson.Gson;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Pergunta;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.RespostaDada;
import br.com.fabioalvaro.tarefaFacil.repository.PerguntaRepository;

/**
 * @author faapereira
 *
 */
public class RespostaDadaForm {
	@NotNull
	@Min(1)
	private Long idPergunta ;
	@NotEmpty(message = "Deve ser respondida a resposta linha")
	private List<String> respostaLinha;
	
	
	
	
	public RespostaDada converter(PerguntaRepository perguntaRepository) {
		//Buscando a Pergunta detalhe do Form
		Optional<Pergunta> pergunta = perguntaRepository.findById(idPergunta);
		if (pergunta.isPresent()) {
			//achou a Pergunta!!!
			
			
			String respostaLinhaAsJson = new Gson().toJson(respostaLinha);
			System.out.println(respostaLinha);
			System.out.println(respostaLinhaAsJson);
			
			//cria RespostaDada
			return new RespostaDada(pergunta.get(),respostaLinhaAsJson,respostaLinha);

			
		}else {
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



	@Override
	public String toString() {
		return "RespostaDadaForm [idPergunta=" + idPergunta + ", ConteudoResposta=" + respostaLinha + "]";
	}
	
	

}
