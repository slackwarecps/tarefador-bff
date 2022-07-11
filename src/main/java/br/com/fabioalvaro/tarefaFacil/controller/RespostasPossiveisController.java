/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fabioalvaro.tarefaFacil.config.security.TokenService;
import br.com.fabioalvaro.tarefaFacil.controller.dto.DetalhesDaRespostaPossivelDto;
import br.com.fabioalvaro.tarefaFacil.controller.dto.RespostaPossivelDto;
import br.com.fabioalvaro.tarefaFacil.controller.form.AtualizacaoRespostaPossivelForm;
import br.com.fabioalvaro.tarefaFacil.controller.form.RespostaPossivelForm;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Pergunta;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.RespostaPossivel;
import br.com.fabioalvaro.tarefaFacil.repository.ListaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.PerguntaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.RespostaPossivelRepository;
import br.com.fabioalvaro.tarefaFacil.repository.UsuarioRepository;
import br.com.fabioalvaro.tarefaFacil.service.FiltroDoUsuarioService;
import br.com.fabioalvaro.tarefaFacil.utils.ErroPadrao;

/**
 * @author faapereira
 *
 */
@RestController
@RequestMapping("/respostaspossiveis")
public class RespostasPossiveisController {
	// Bean que acessa o banco de dados
	@Autowired
	private RespostaPossivelRepository respostaPossivelRepository;
	@Autowired
	private PerguntaRepository perguntaRepository;
	
	
	@Autowired
	private FiltroDoUsuarioService filtroDoUsuario;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private ListaRepository listaRepository;
	@Autowired
	private ErroPadrao erroPadrao;
	
	// BUSCA TODOS
	@GetMapping
	public Page<RespostaPossivelDto> lista(
			@RequestHeader("Authorization") String authorizationHeader,
			@RequestParam(required = true) Long idPergunta,
		@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		// Paginacao
		if (idPergunta == null) {
			Page<RespostaPossivel> respostasPossiveis = respostaPossivelRepository.findAll(paginacao);
			return RespostaPossivelDto.converter(respostasPossiveis);
		} else {
			
			
			//Qual a Lista dessa Pergunta
			Long idLista = null;
			Optional<Pergunta> perguntaLocalizada =  perguntaRepository.findById(idPergunta);
			if (perguntaLocalizada.isPresent()) {
				idLista = perguntaLocalizada.get().getLista().getId();
			}
			
			
			
			// Permissões
			// Essa lista é de uma rede que voce tem autorizacao para buscar?
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository, idLista)) {
				System.out.println("Nao tem acesso as possiveis respostas dessa pergunta/lista....");
				Page<RespostaPossivel> respostasPossiveis = respostaPossivelRepository.findByPerguntaId(-1L, paginacao);
				return RespostaPossivelDto.converter(respostasPossiveis);
			}
			// Fim Permissões
			
			
			
			
			Page<RespostaPossivel> respostasPossiveis = respostaPossivelRepository.findByPerguntaId(idPergunta, paginacao);
			return RespostaPossivelDto.converter(respostasPossiveis);
		}
	}

	// CADASTRAR

	@PostMapping(produces = { "application/json" })
	public ResponseEntity<?> cadastrar(
			@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody @Valid RespostaPossivelForm form, UriComponentsBuilder uriBuilder) {
		// Converte o form no Operador
		form.toString();
		//injeto o foreing no form pra buscar o fk
		RespostaPossivel respostaPossivel = form.converter(perguntaRepository);	
		if (respostaPossivel == null) {
			System.out.println("Pergunta Invalida null!");			
			return ResponseEntity.notFound().build();
		}
		
		// Permissões
		// Essa lista é de uma rede que voce tem autorizacao para buscar?
		filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
		if (filtroDoUsuario.isPermitidoAcessarLista(listaRepository, respostaPossivel.getPergunta().getLista().getId()) == false) {
			return erroPadrao.badRequest("idLista", "Você não pode cadastrar para essa pergunta/lista. #Permissoes");
		}
		// Fim Permissões
		
		
		
		
		
		// Salva a Resposa Possivel
		respostaPossivelRepository.save(respostaPossivel);
		URI uri = uriBuilder.path("/respostaspossiveis/{id}").buildAndExpand(respostaPossivel.getId()).toUri();
		return ResponseEntity.created(uri).body(new RespostaPossivelDto(respostaPossivel));
	}


	
	// UPDATE
	@PutMapping("/{id}")
	@Transactional
	@PostMapping(produces = { "application/json" })
	public ResponseEntity<?> atualizar(
			@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id, @RequestBody @Valid AtualizacaoRespostaPossivelForm form) {
		Optional<RespostaPossivel> respostaPossivelLocalizada = respostaPossivelRepository.findById(id);
		if (respostaPossivelLocalizada.isPresent()) {
			
			// Permissões
			// Essa lista é de uma rede que voce tem autorizacao para buscar?
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository,
					respostaPossivelLocalizada.get().getPergunta().getLista().getId())) {
				return erroPadrao.badRequest("idLista", "Você não pode atualizar esta pergunta/lista. #Permissoes");
			}
			// Fim Permissões
			
			
			
			
			RespostaPossivel respostaPossivel = form.atualizar(id, respostaPossivelRepository);
			return ResponseEntity.ok(new RespostaPossivelDto(respostaPossivel));
		}
		return ResponseEntity.notFound().build();

	}
	
	// DETALHAR POR ID
	@GetMapping("/{id}")
	@PostMapping(produces = { "application/json" })
	public ResponseEntity<?> detalhar(
			@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id) {		
		Optional<RespostaPossivel> respostasPossiveis = respostaPossivelRepository.findById(id);
		if (respostasPossiveis.isPresent()) {
			
			
			
			// Permissões
			// Essa lista é de uma rede que voce tem autorizacao para buscar?
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository, respostasPossiveis.get().getPergunta().getLista().getId())) {
				return erroPadrao.badRequest("idLista", "Você não pode ver detalhes desta respostaPossivel/pergunta/lista. #Permissoes");
			}
			// Fim Permissões
			
			
			
			
			return ResponseEntity.ok(new DetalhesDaRespostaPossivelDto(respostasPossiveis.get()));
		}
		return ResponseEntity.notFound().build();
	}

	// DELETE
	@DeleteMapping("/{id}")
	@PostMapping(produces = { "application/json" })
	@Transactional
	public ResponseEntity<?> remover(@RequestHeader("Authorization") String authorizationHeader,@PathVariable Long id) {
		Optional<RespostaPossivel> respostaPossivelLocalizada = respostaPossivelRepository.findById(id);
		if (respostaPossivelLocalizada.isPresent()) {
			
			// Permissões
			// Essa lista é de uma rede que voce tem autorizacao para buscar?
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository, respostaPossivelLocalizada.get().getPergunta().getLista().getId())) {
				return erroPadrao.badRequest("idLista", "Você não pode excluir esta respostaPossivel/pergunta/lista. #Permissoes");
			}
			// Fim Permissões
			
			
			
			respostaPossivelRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
