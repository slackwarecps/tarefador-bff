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
import br.com.fabioalvaro.tarefaFacil.controller.dto.AcaoDto;
import br.com.fabioalvaro.tarefaFacil.controller.dto.DetalhesDaAcaoDto;
import br.com.fabioalvaro.tarefaFacil.controller.form.AcaoForm;
import br.com.fabioalvaro.tarefaFacil.controller.form.AtualizacaoAcaoForm;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Acao;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Lista;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Pergunta;
import br.com.fabioalvaro.tarefaFacil.repository.AcaoRepository;
import br.com.fabioalvaro.tarefaFacil.repository.ListaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.PerguntaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.UsuarioRepository;
import br.com.fabioalvaro.tarefaFacil.service.FiltroDoUsuarioService;
import br.com.fabioalvaro.tarefaFacil.utils.ErroPadrao;

/**
 * @author faapereira
 *
 */
@RestController
@RequestMapping("/acoes")
public class AcoesController {
	// Bean que acessa o banco de dados
	// Bean que acessa o banco de dados
	@Autowired
	private AcaoRepository acaoRepository;
	@Autowired
	private PerguntaRepository perguntaRepository;
	@Autowired
	private ListaRepository listaRepository;
	@Autowired
	private FiltroDoUsuarioService filtroDoUsuario;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private ErroPadrao erroPadrao;

	// BUSCA TODAS AS ACOES
	@GetMapping
	public Page<AcaoDto> lista(@RequestHeader("Authorization") String authorizationHeader,
			@RequestParam(required = true) Long idPergunta,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		// Paginacao
		if (idPergunta == null) {
			Page<Acao> acoes = acaoRepository.findAll(paginacao);
			return AcaoDto.converter(acoes);
		} else {

			// Qual a Lista dessa Pergunta desta ACAO
			Long idLista = null;
			Optional<Pergunta> perguntaLocalizada = perguntaRepository.findById(idPergunta);
			if (perguntaLocalizada.isPresent()) {
				idLista = perguntaLocalizada.get().getLista().getId();
			}

			// Permissões
			// Essa lista é de uma rede que voce tem autorizacao para buscar?
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository, idLista)) {
				System.out.println("Nao tem acesso as possiveis respostas dessa pergunta/lista....");
				Page<Acao> acoes = acaoRepository.findByPerguntaId(-1l, paginacao);
				return AcaoDto.converter(acoes);
			}
			// Fim Permissões

			Page<Acao> acoes = acaoRepository.findByPerguntaId(idPergunta, paginacao);
			return AcaoDto.converter(acoes);
		}
	}

	// CADASTRAR
	@PostMapping(produces = { "application/json" })
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody @Valid AcaoForm form, UriComponentsBuilder uriBuilder) {

//		Long contador;
//		contador = acaoRepository.countByPerguntaId(form.getIdPergunta());
//		if (contador.intValue() > 0) {					
//			return erroPadrao.badRequest("idPergunta", "Pergunta ja possui uma acao associada. ");
//		}
		
		
		
		// Existe a pergunta que ele esta tentando atualizar?
		// achar a pergunta e sua Lista referente
		Optional<Pergunta> perguntaLocalizada = perguntaRepository.findById(form.getIdPergunta());
		if (!perguntaLocalizada.isPresent()) {
			return erroPadrao.badRequest("idPergunta", "Pergunta não Localizada.");
		}
		//Validando Lista Vinculada
		Optional<Lista> listaLocalizada = listaRepository.findById(form.getIdListaVinculada());
		if (!listaLocalizada.isPresent()) {
			return erroPadrao.badRequest("idListaVinculada", "Lista Vinculada não Localizada.");
		}			
		// converte o form no topico
		Acao acao = form.converter(listaRepository, perguntaRepository);
		
		// Permissões
		Long idListaVinculada = form.getIdListaVinculada();
		// Essa lista é de uma rede que voce tem autorizacao para buscar?
		filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
		if (filtroDoUsuario.isPermitidoAcessarLista(listaRepository, acao.getPergunta().getLista().getId()) == false) {
			return erroPadrao.badRequest("idPergunta","Você não pode cadastrar para essa ação / Pergunta / lista. #Permissoes");
		}
		if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository, idListaVinculada)) {
			return erroPadrao.badRequest("idLista","Você não pode atualizar esta ação / pergunta /lista. Pois a Lista Vinculada não é permitida. #Permissoes");
		}
		// Fim Permissões

		acaoRepository.save(acao);
		// muda flag da acao na pergunta
		Optional<Pergunta> pergunta = perguntaRepository.findById(acao.getPergunta().getId());
		if (pergunta.isPresent()) {

			pergunta.get().setTemAcao(true);
		}

		URI uri = uriBuilder.path("/redes/{id}").buildAndExpand(acao.getId()).toUri();

		return ResponseEntity.created(uri).body(new AcaoDto(acao));

	}

	// UPDATE
	@PutMapping("/{id}")
	@Transactional
	@PostMapping(produces = { "application/json" })
	public ResponseEntity<?> atualizar(@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id, @RequestBody @Valid AtualizacaoAcaoForm form) {
		Optional<Acao> acaoLocalizada = acaoRepository.findById(id);
		if (acaoLocalizada.isPresent()) {
			System.out.println("Acao Localizada,,,,");
			Long idListaVinculada = form.getIdListaVinculada();
			Long idPerguntaASerAtualizada = form.getIdPergunta();

			// Permissões Dupla
			// Essa lista é de uma rede que voce tem autorizacao para buscar? #Fase 1 - Dono
			// da Pergunta
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository,
					acaoLocalizada.get().getPergunta().getLista().getId())) {
				return erroPadrao.badRequest("idLista",
						"Você não pode atualizar esta ação / pergunta /lista. #OWNER. #Permissoes");
			}

			// Validando as lista de ele esta tentando colocar..... #Fase 2

			System.out.println("Vinculada do Update " + idListaVinculada);
			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository, idListaVinculada)) {
				return erroPadrao.badRequest("idLista",
						"Você não pode atualizar esta ação / pergunta /lista. Pois a Lista Vinculada não é permitida. #Permissoes");
			}

			System.out.println("Nova Pergunta do Update " + idListaVinculada);

			// achar a pergunta e sua Lista referente
			Long idListaAserAtualizada = -1L;
			Optional<Pergunta> perguntaLocalizada = perguntaRepository.findById(idPerguntaASerAtualizada);
			if (perguntaLocalizada.isPresent()) {
				idListaAserAtualizada = perguntaLocalizada.get().getLista().getId();
			} else {
				return erroPadrao.badRequest("idPergunta", "Pergunta não Localizada.");
			}
			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository, idListaAserAtualizada)) {
				return erroPadrao.badRequest("idLista",
						"Você não pode atualizar esta ação / pergunta /lista. Pois a pergunta destino não é permitida. #Permissoes");
			}

			// Fim Permissões

			Acao acao = form.atualizar(id, acaoRepository);

			if (acao == null) {
				System.out.println("pergunta null!");
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok(new AcaoDto(acao));
		}
		return ResponseEntity.notFound().build();

	}

	// DETALHAR POR ID
	@GetMapping("/{id}")
	@PostMapping(produces = { "application/json" })
	public ResponseEntity<?> detalhar(@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id) {

		Optional<Acao> acao = acaoRepository.findById(id);
		if (acao.isPresent()) {
			
			// Permissões
			// Essa lista é de uma rede que voce tem autorizacao para buscar?
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository, acao.get().getPergunta().getLista().getId())) {
				return erroPadrao.badRequest("idAcao", "Você não pode ver detalhes desta ação / pergunta /lista. #Permissoes");
			}
			// Fim Permissões
			
			return ResponseEntity.ok(new DetalhesDaAcaoDto(acao.get()));
		}

		return ResponseEntity.notFound().build();
	}

	// DELETE
	@DeleteMapping("/{id}")
	@PostMapping(produces = { "application/json" })
	@Transactional
	public ResponseEntity<?> remover(@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id) {
		Optional<Acao> acaoLocalizada = acaoRepository.findById(id);
		if (acaoLocalizada.isPresent()) {
			
			
			// Permissões
			// Essa lista é de uma rede que voce tem autorizacao para buscar?
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository, acaoLocalizada.get().getPergunta().getLista().getId())) {
				return erroPadrao.badRequest("idLista", "Você não pode excluir essa ação / pergunta /lista. #Permissoes");
			}
			// Fim Permissões
			
			
			
			acaoRepository.deleteById(id);

			// muda flag da acao na pergunta
			Optional<Pergunta> pergunta = perguntaRepository.findById(acaoLocalizada.get().getPergunta().getId());
			pergunta.get().setTemAcao(false);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}
