/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Acao;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Lista;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Pergunta;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.RespostaDada;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.RespostaPossivel;
import br.com.fabioalvaro.tarefaFacil.repository.AcaoRepository;
import br.com.fabioalvaro.tarefaFacil.repository.PerguntaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.RespostaPossivelRepository;

/**
 * @author faapereira
 *
 */
@Service
public class AcaoService {
	@Autowired
	private RespostaPossivelRepository respostaPossivelRepository;
	@Autowired
	private PerguntaRepository perguntaPossivelRepository;

	@Autowired
	private EncaminharListaService encaminharListaService;

	Optional<Acao> acao;

	public void processarAcao(RespostaDada respostaDada, AcaoRepository acaoRepository) {
		System.out.println("");
		System.out.println("Processando acao ");
		System.out.println("------------------------------------------------------");
		System.out.println(respostaDada);
		System.out.println("busca a acao dessa pergunta");
		acao = acaoRepository.findByPerguntaId(respostaDada.getPergunta().getId());
		if (acao.isPresent()) {
			System.out.println(acao.toString());
			// Por Resposta
			if (acao.get().getGatilho().toString().equals("PERGUNTA.RESPOSTA")) {
				if (acao.get().getGatilhoRegra().toString().equals("=")) {
					// Igualdade
					// Se as respostas dadas (itens) contem a resposta esperada entao esta correto.
					if (respostaDada.getRespostaItem().contains(acao.get().getGatilhoValor())) {
						System.out.println("LETS GO ACTION!!! bateu Resposta!!! com a ação....");
						Lista listaEncaminhar;
						listaEncaminhar = acao.get().getListaVinculada();
						System.out.println("Respostas Possiveis que irei encaminhar...");
						List<RespostaPossivel> listaRespostaPossiveis = respostaPossivelRepository
								.findAllByPerguntaId(respostaDada.getPergunta().getId());
						listaRespostaPossiveis.forEach(item -> System.out.println(item.toString()));
						Optional<Pergunta> perguntaRespondida;
						perguntaRespondida = perguntaPossivelRepository.findById(acao.get().getPergunta().getId());
						encaminharListaService.encaminhaLista(listaEncaminhar, perguntaRespondida.get(),listaRespostaPossiveis, acao.get(),0L);
					} else {
						System.out.println("Resposta não correspondeu...");
					}
				}
			}
		} else {
			System.out.println("Nao achei Acao para essa pergunta! ");
		}
		System.out.println("");
		System.out.println("Fim do Processa Acao");
		System.out.println("   ");
	}
	
	public void processarVariasAcoes(RespostaDada respostaDada, AcaoRepository acaoRepository) {
		System.out.println("");
		System.out.println("Processando Varias acoes ");
		System.out.println("------------------------------------------------------");
		System.out.println(respostaDada);

		
		List<Acao> listaDeAcoes  = acaoRepository.findAllByPerguntaId(respostaDada.getPergunta().getId());
		listaDeAcoes.forEach(acaoItem->{
			System.out.println("ACAO: "+ acaoItem.getDescricao());
			System.out.println("==============================");
			//System.out.println(acao.toString());
			Long valorQuantidade =respostaDada.getPergunta().getQuantidade();
			
			Long num= Long.parseLong(respostaDada.getRespostaItem().get(0));
			System.out.println("Resposta Dada:");
			respostaDada.getRespostaItem().forEach(resposta->{
				System.out.println(resposta);
			});
			
			// Por Resposta
			if (acaoItem.getGatilho().toString().equals("PERGUNTA.RESPOSTA")) {
				
				if (acaoItem.getGatilhoRegra().toString().equals("ajuste_estoque")) {
					System.out.println("ENTROU !!! Condicao: AJUSTE_ESTOQUE");
					System.out.println("Quantidade da pergunta: "+ valorQuantidade);
					System.out.println("NUM RESPOSTA: "+ num);
					if ( (num!= 0) && (num!=valorQuantidade ) ) {
						System.out.println("!!!!!! Condicao do Alterar estoque - BATEU !!!!!! ");
						Lista listaEncaminhar;
						listaEncaminhar = acaoItem.getListaVinculada();						
						List<RespostaPossivel> listaRespostaPossiveis = respostaPossivelRepository.findAllByPerguntaId(respostaDada.getPergunta().getId());						
						Optional<Pergunta> perguntaRespondida;
						perguntaRespondida = perguntaPossivelRepository.findById(acaoItem.getPergunta().getId());
						//perguntaRespondida.get().setQuantidade(num);
						//REmover o texto da Pergunta Original			
						
						String descricaoPergunta =perguntaRespondida.get().getDescricao();
//						System.out.println(descricaoPergunta);
//						String novaDescricao = descricaoPergunta.replace("Quantidade 84","xxx");
//						System.out.println(novaDescricao);
						//perguntaRespondida.get().setDescricao(novaDescricao);
						
						
						encaminharListaService.encaminhaLista(listaEncaminhar, perguntaRespondida.get(),listaRespostaPossiveis, acaoItem,num);
					}else {
						System.out.println("nao entrou na condição.");
					}
					
					
				}
				
				
				
				
				
				if (acaoItem.getGatilhoRegra().toString().equals("=")) {
					// Igualdade
					// Se as respostas dadas (itens) contem a resposta esperada entao esta correto.
					if (respostaDada.getRespostaItem().contains(acaoItem.getGatilhoValor())) {						
						Lista listaEncaminhar;
						listaEncaminhar = acaoItem.getListaVinculada();						
						List<RespostaPossivel> listaRespostaPossiveis = respostaPossivelRepository.findAllByPerguntaId(respostaDada.getPergunta().getId());						
						Optional<Pergunta> perguntaRespondida;
						perguntaRespondida = perguntaPossivelRepository.findById(acaoItem.getPergunta().getId());
						encaminharListaService.encaminhaLista(listaEncaminhar, perguntaRespondida.get(),listaRespostaPossiveis, acaoItem,0L);
					} else {
						System.out.println("Resposta não correspondeu...");
					}
				}
			}
		
			
			
			
		}//loop foreach
		);
		
		
		
		

		
		
		
		
		System.out.println("");
		System.out.println("Fim do Processar Acoes");
		System.out.println("   ");
	}
	

}
