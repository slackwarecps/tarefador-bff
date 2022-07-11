package br.com.fabioalvaro.tarefaFacil.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import br.com.fabioalvaro.tarefaFacil.controller.dto.DetalhesDaPerguntaDto;
import br.com.fabioalvaro.tarefaFacil.controller.dto.PerguntaDto;
import br.com.fabioalvaro.tarefaFacil.controller.form.AtualizacaoPerguntaForm;
import br.com.fabioalvaro.tarefaFacil.controller.form.PerguntaForm;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Pergunta;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.PerguntaTipo;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.RespostaPossivel;
import br.com.fabioalvaro.tarefaFacil.repository.AcaoRepository;
import br.com.fabioalvaro.tarefaFacil.repository.ListaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.PerguntaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.RespostaDadaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.RespostaPossivelRepository;
import br.com.fabioalvaro.tarefaFacil.repository.UsuarioRepository;
import br.com.fabioalvaro.tarefaFacil.service.FiltroDoUsuarioService;
import br.com.fabioalvaro.tarefaFacil.utils.ErroPadrao;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/perguntas")
public class PerguntasController {
	@Autowired
	private PerguntaRepository perguntaRepository;

	@Autowired
	private ListaRepository listaRepository;
	@Autowired
	private AcaoRepository acaoRepository;
	@Autowired
	private RespostaDadaRepository respostaDadaRepository;
	@Autowired
	private RespostaPossivelRepository respostaPossivelRepository;

	@Autowired
	private ErroPadrao erroPadrao;

	@Autowired
	private FiltroDoUsuarioService filtroDoUsuario;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UsuarioRepository usuarioRepository;

	// BUSCA E RETORNA TODOS
	@GetMapping
	public Page<PerguntaDto> lista(@RequestHeader("Authorization") String authorizationHeader,
			@RequestParam(required = true) Long idLista,
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		// Paginacao

		// Permissões
		// Essa lista é de uma rede que voce tem autorizacao para buscar?
		filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
		if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository, idLista)) {
			System.out.println("Nao tem acesso a lista....");
			Page<Pergunta> perguntas = perguntaRepository.findByListaId(-1L, paginacao);
			return PerguntaDto.converter(perguntas);
		}
		// Fim Permissões
		

		if (idLista == null) {
			Page<Pergunta> perguntas = perguntaRepository.findAll(paginacao);
			return PerguntaDto.converter(perguntas);
		} else {
			Page<Pergunta> perguntas = perguntaRepository.findByListaId(idLista, paginacao);
			return PerguntaDto.converter(perguntas);
		}
	}

	// CADASTRAR
	@PostMapping(produces = { "application/json" })
	public ResponseEntity<?> cadastrar(@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody @Valid PerguntaForm form, UriComponentsBuilder uriBuilder) {
		System.out.println("entro no cadastrar");
		// Converte o form no topico
		// form.toString();
		Pergunta pergunta = form.converter(listaRepository);
		if (pergunta == null) {
			System.out.println("pergunta nula!");
			return ResponseEntity.notFound().build();
		}

		// Permissões
		// Essa lista é de uma rede que voce tem autorizacao para buscar?
		filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
		if (filtroDoUsuario.isPermitidoAcessarLista(listaRepository, pergunta.getLista().getId()) == false) {
			return erroPadrao.badRequest("idLista", "Você não pode cadastrar esta lista. #Permissoes");
		}
		// Fim Permissões

		// VALIDAR SE A LISTA é DE TREINAMENTO
		if ((pergunta.getPerguntaTipo().equals(PerguntaTipo.TREINAMENTO) && pergunta.getLinkVideo() == null)) {
			return erroPadrao.badRequest("perguntaTipo",
					"Toda pergunta de TREINAMENTO tem que ter um linkVideo associado.");
		}

		// se apergunta for do tipo TREINAMENTO
		// verificar se o tipo foi igual a BOOLEAN

		// Salva o topico
		perguntaRepository.save(pergunta);
		URI uri = uriBuilder.path("/perguntas/{id}").buildAndExpand(pergunta.getId()).toUri();
		return ResponseEntity.created(uri).body(new PerguntaDto(pergunta));

	}

	// UPDATE
	@PutMapping("/{id}")
	@PostMapping(produces = { "application/json" })
	@Transactional
	public ResponseEntity<?> atualizar(@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id, @RequestBody @Valid AtualizacaoPerguntaForm form) {

		Optional<Pergunta> perguntaLocalizada = perguntaRepository.findById(id);
		if (perguntaLocalizada.isPresent()) {

			// Permissões
			// Essa lista é de uma rede que voce tem autorizacao para buscar?
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository,
					perguntaLocalizada.get().getLista().getId())) {
				return erroPadrao.badRequest("idLista", "Você não pode atualizar esta lista. #Permissoes");
			}
			// Fim Permissões

			Pergunta pergunta = form.atualizar(id, perguntaRepository);
			return ResponseEntity.ok(new PerguntaDto(pergunta));
		}
		return ResponseEntity.notFound().build();

	}

	// DETALHAR POR ID
	@GetMapping("/{id}")
	@PostMapping(produces = { "application/json" })
	public ResponseEntity<?> detalhar(@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id) {

		Optional<Pergunta> pergunta = perguntaRepository.findById(id);
		if (pergunta.isPresent()) {

			// Permissões
			// Essa lista é de uma rede que voce tem autorizacao para buscar?
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository, pergunta.get().getLista().getId())) {
				return erroPadrao.badRequest("idLista", "Você não pode ver detalhes desta lista. #Permissoes");
			}
			// Fim Permissões

			return ResponseEntity.ok(new DetalhesDaPerguntaDto(pergunta.get()));
		}

		return ResponseEntity.notFound().build();
	}

	// DELETE
	@DeleteMapping("/{id}")
	@PostMapping(produces = { "application/json" })
	@Transactional
	public ResponseEntity<?> remover(@RequestHeader("Authorization") String authorizationHeader,@PathVariable Long id) {
		Optional<Pergunta> optional = perguntaRepository.findById(id);
		if (optional.isPresent()) {

			// Permissões
			// Essa lista é de uma rede que voce tem autorizacao para buscar?
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository, optional.get().getLista().getId())) {
				return erroPadrao.badRequest("idLista", "Você não pode excluir esta lista. #Permissoes");
			}
			// Fim Permissões

			// apagar recursivamente
			System.out.println("");
			System.out.println("Apagando Recursivamente...");
			System.out.println("-------------------------------");

			// acoes
			try {
				acaoRepository.deleteByPerguntaId(optional.get().getId());
				System.out.println("apagou a acao relacionada");
			} catch (Exception e) {
				System.out.println("Nao conseguiu excluir a Ação dessa pergunta " + optional.get().getId());
			}

			Long perguntaId = optional.get().getId();
			System.out.println("Pergunta id: " + perguntaId);
			// respostas possiveis

			List<RespostaPossivel> lista = respostaPossivelRepository.findAllByPerguntaId(perguntaId);

			System.out.println("foreach...");
			lista.forEach(item -> {
				System.out.println("achou! " + item.getId());

			});
			respostaPossivelRepository.deleteByPerguntaId(perguntaId);
			System.out.println("apagou as respostas possiveis  relacionadas");

			// respostas dadas
			try {
				respostaDadaRepository.deleteByPerguntaId(optional.get().getId());
				System.out.println("apagou a resposta dada relacionada");
			} catch (Exception e) {
				System.out.println("Nao conseguiu excluir a resposta dada dessa pergunta " + optional.get().getId());
			}

			perguntaRepository.deleteById(id);
			System.out.println("Pergunta excluida completamente!");

			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}
