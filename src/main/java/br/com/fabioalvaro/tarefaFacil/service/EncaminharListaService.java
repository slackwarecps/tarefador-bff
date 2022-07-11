/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Acao;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Lista;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Pergunta;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.PerguntaStatus;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.RespostaPossivel;
import br.com.fabioalvaro.tarefaFacil.repository.PerguntaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.RespostaPossivelRepository;

/**
 * @author faapereira
 *
 */
@Service
public class EncaminharListaService {
	
	@Autowired
	private PerguntaRepository perguntaRepository;
	@Autowired
	private RespostaPossivelRepository respostaPossivelRepository;
	
	
	
	public void encaminhaLista(Lista listaDestino, Pergunta perguntaRespondida, List<RespostaPossivel> listaRespostaPossiveis, Acao acao,Long novaQuantidade) {
		System.out.println("");
		System.out.println("Encaminhando Pergunta para Lista");
		System.out.println("=========================================");
		System.out.println("Lista Destino: "+ listaDestino.getId());
		System.out.println("Pergunta Respondida: "+ perguntaRespondida.getId());
		System.out.println("Respostas Possiveis de Origem:");
		listaRespostaPossiveis.forEach(  item->{
			System.out.println(item.toString());
		}   );
		System.out.println("");
		//Cria a Pergunta
		Pergunta perguntaNova;
		
		perguntaNova = new Pergunta(acao.getPrefixoPerguntaDescricao()+perguntaRespondida.getDescricao(), 
				perguntaRespondida.getObrigatoria(), 
				perguntaRespondida.getPesoPergunta(), 
				listaDestino, 
				PerguntaStatus.NAO_RESPONDIDA, 
				perguntaRespondida.getPerguntaTipo(),
				perguntaRespondida.getLinkVideo(),
				novaQuantidade);
		perguntaRepository.save(perguntaNova);
		System.out.println("Pergunta NOVA CRIADA");
		System.out.println(perguntaNova.getId() + " lista: "+perguntaNova.getLista().getId());
		
		//Cria as Respostas Possiveis
		System.out.println("Possiveis respostas da nova pergunta:");
		listaRespostaPossiveis.forEach(respostaPossivelItemitem->{
			
			System.out.println(respostaPossivelItemitem.getDescricao());
		
			respostaPossivelRepository.save(new RespostaPossivel(respostaPossivelItemitem.getDescricao(),perguntaNova));
			
		});
	
		
		
		
		System.out.println("Pergunta Encaminhada para Lista com sucesso!!");
	}
}
