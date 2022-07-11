/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fabioalvaro.tarefaFacil.config.security.TokenService;
import br.com.fabioalvaro.tarefaFacil.controller.dto.DetalhesDaRespostaDadaDto;
import br.com.fabioalvaro.tarefaFacil.controller.dto.RespostaDadaDto;
import br.com.fabioalvaro.tarefaFacil.controller.dto.RespostaLinha;
import br.com.fabioalvaro.tarefaFacil.controller.form.AtualizacaoRespostaDadaForm;
import br.com.fabioalvaro.tarefaFacil.controller.form.RespostaDadaForm;
import br.com.fabioalvaro.tarefaFacil.controller.form.RespostaDadaMultiplaForm;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Pergunta;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.PerguntaStatus;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.RespostaDada;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.TipoProcessaAcao;
import br.com.fabioalvaro.tarefaFacil.repository.AcaoRepository;
import br.com.fabioalvaro.tarefaFacil.repository.ListaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.PerguntaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.RespostaDadaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.UsuarioRepository;
import br.com.fabioalvaro.tarefaFacil.service.AcaoService;
import br.com.fabioalvaro.tarefaFacil.service.AmazonClient;
import br.com.fabioalvaro.tarefaFacil.service.FiltroDoUsuarioService;
import br.com.fabioalvaro.tarefaFacil.utils.ErroPadrao;

/**
 * @author faapereira
 *
 */
@RestController
@RequestMapping("/respostasdadas")
public class RespostasDadasController {

	private AmazonClient amazonClient;

	@Autowired
	private RespostaDadaRepository respostadadaRepository;

	@Autowired
	private PerguntaRepository perguntaRepository;

	@Autowired
	private AcaoRepository acaoRepository;

	@Autowired
	private AcaoService acaoService;

	@Autowired
	private ErroPadrao erroPadrao;

	@Autowired
	private FiltroDoUsuarioService filtroDoUsuario;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private ListaRepository listaRepository;

	@Autowired
	RespostasDadasController(AmazonClient amazonClient) {
		System.out.println("inicializou o client do s3...");
		this.amazonClient = amazonClient;
	}

	// BUSCA TODOS
	@GetMapping
	public Page<RespostaDadaDto> lista(@RequestHeader("Authorization") String authorizationHeader,
			@RequestParam(required = true) Long idPergunta,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		// Paginacao
		if (idPergunta == null) {
			Page<RespostaDada> respostasDadas = respostadadaRepository.findAll(paginacao);
			return RespostaDadaDto.converter(respostasDadas);
		} else {

			System.out.println("Respostas Dadas.... GET");

			// Permissões
			// Essa lista é de uma rede que voce tem autorizacao para buscar?
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			Long idLista = filtroDoUsuario.RecuperaIdListaPorPerguntaId(idPergunta, perguntaRepository);

			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository, idLista)) {
				System.out.println("Nao tem acesso a lista....");
				Page<RespostaDada> respostasDadas = respostadadaRepository.findByPerguntaId(-1L, paginacao);
				return RespostaDadaDto.converter(respostasDadas);
			}
			// Fim Permissões

			System.out.println("Vou responder...");
			Page<RespostaDada> respostasDadas = respostadadaRepository.findByPerguntaId(idPergunta, paginacao);
			return RespostaDadaDto.converter(respostasDadas);
		}
	}

	// CADASTRAR UMA RESPOSTA DE UMA PERGUNTA
	@PostMapping(produces = { "application/json" })
	public ResponseEntity<?> cadastrar(@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody @Valid RespostaDadaForm form, UriComponentsBuilder uriBuilder) {

		// Somente uma resposta por Pergunta
		Long contador;
		contador = respostadadaRepository.countByPerguntaId((long) form.getIdPergunta());
		if (contador > 0) {
			System.out.println("Ja foi respondida");
			// return ResponseEntity.badRequest().build();
			// return erroPadrao.badRequest("idPergunta", "Essa Pergunta Ja Foi
			// respondida");
		}

		// Converte o form na Resposta Dada
		RespostaDada respostaDada = form.converter(perguntaRepository);

		System.out.println(respostaDada);
		if (respostaDada == null) {
			System.out.println("Pergunta nula!");
			return ResponseEntity.notFound().build();
		}
		
		
		// Permissões
		// Essa lista é de uma rede que voce tem autorizacao para buscar?
		filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
		if (filtroDoUsuario.isPermitidoAcessarLista(listaRepository, respostaDada.getPergunta().getLista().getId()) == false) {
			return erroPadrao.badRequest("idPergunta", "Você não pode Responder esta Resposta/ Pergunta/ lista. #Permissoes");
		}
		// Fim Permissões
		
		

		//ir na pergunta e mudar o status
		Optional<Pergunta> pergunta = perguntaRepository.findById(respostaDada.getPergunta().getId());
		if (pergunta.isPresent()){
			pergunta.get().setPerguntaStatus(PerguntaStatus.RESPONDIDA);
			System.out.println("mudei status da pergunta");
		}
		
		// Salva a Resposta dada
		
		respostadadaRepository.save(respostaDada);

		// Tem acao?
		System.out.println("Essa pergunta tem ação? " + respostaDada.getPergunta().getTemAcao());

		System.out.println(respostaDada);

		// if(1==1) return erroPadrao.badRequest("DEBUG", "Chegou!!!");

		
		if ((respostaDada.getPergunta().getTemAcao() == true)) {
			// Por enquanto fixo o tipo do processamento hardcoded
			TipoProcessaAcao tipoProcessaAcao = TipoProcessaAcao.PROCESSAMENTO_INDIVIDUAL;
			if (tipoProcessaAcao == TipoProcessaAcao.PROCESSAMENTO_INDIVIDUAL) {
				// acaoService.processarAcao(respostaDada, acaoRepository);
				 acaoService.processarVariasAcoes(respostaDada, acaoRepository);
			}

		}

		// Retorna
		URI uri = uriBuilder.path("/respostasdadas/{id}").buildAndExpand(respostaDada.getId()).toUri();
		return ResponseEntity.created(uri).body(new RespostaDadaDto(respostaDada));

	}

	// UPDATE
	@PutMapping("/{id}")
	@PostMapping(produces = { "application/json" })
	@Transactional
	public ResponseEntity<?> atualizar(
			@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id, 
			@RequestBody @Valid AtualizacaoRespostaDadaForm form) {
		Optional<RespostaDada> respostaDadaLocalizada = respostadadaRepository.findById(id);
		if (respostaDadaLocalizada.isPresent()) {
			RespostaDada respostaDada = form.atualizar(id, respostadadaRepository);

			if (respostaDada == null) {
				System.out.println("pergunta null!");
				return ResponseEntity.notFound().build();
			}

			// Permissões
			// Essa lista é de uma rede que voce tem autorizacao para buscar?
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository,
					respostaDadaLocalizada.get().getPergunta().getLista().getId())) {
				return erroPadrao.badRequest("idLista", "Você não pode atualizar desta RespostaDada/ pergunta/ lista. #Permissoes");
			}
			// Fim Permissões

			return ResponseEntity.ok(new RespostaDadaDto(respostaDada));
		}
		return ResponseEntity.notFound().build();

	}

	// DETALHAR POR ID
	@GetMapping("/{id}")
	@PostMapping(produces = { "application/json" })
	public ResponseEntity<?> detalhar(@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id) {

		Optional<RespostaDada> respostaDada = respostadadaRepository.findById(id);
		if (respostaDada.isPresent()) {

			// Permissões
			// Essa lista é de uma rede que voce tem autorizacao para buscar?
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository,
					respostaDada.get().getPergunta().getLista().getId())) {
				return erroPadrao.badRequest("idLista", "Você não pode ver detalhes destaRespostaDada/ Pergunta/ Lista. #Permissoes");
			}
			// Fim Permissões

			return ResponseEntity.ok(new DetalhesDaRespostaDadaDto(respostaDada.get()));
		}

		return ResponseEntity.notFound().build();
	}

	// DELETE
	@DeleteMapping("/{id}")
	@Transactional
	@PostMapping(produces = { "application/json" })
	public ResponseEntity<?> remover(
			@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id) {
		Optional<RespostaDada> optional = respostadadaRepository.findById(id);
		if (optional.isPresent()) {
			// Permissões
			// Essa lista é de uma rede que voce tem autorizacao para buscar?
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository,
					optional.get().getPergunta().getLista().getId())) {
				return erroPadrao.badRequest("idLista", "Você não pode excluir essa RespostaDada/ Pergunta/ Lista. #Permissoes");
			}
			// Fim Permissões
			
			respostadadaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

	// UPLOAD ARQUIVO
	@PostMapping("/uploadArquivo")
	public ResponseEntity<?> uploadFile(@RequestHeader("Authorization") String authorizationHeader,
			@RequestPart(value = "file") @Valid MultipartFile file,
			@RequestParam("idPergunta") @Valid Long idPergunta) {
		System.out.println("ID pergunta: " + idPergunta);

		// Buscando a Pergunta detalhe do Form
		Optional<Pergunta> pergunta = perguntaRepository.findById(idPergunta);
		if (pergunta.isPresent()) {
			// achou a Pergunta!!!

			// 1)sobe o arquivo
			System.out.println("entrou no upload...");
			String filename = "";
			filename = this.amazonClient.uploadFile(file);

//			//2)cria RespostaDada	
			List<String> respostaItem = new ArrayList<>();
			respostaItem.add(filename);
//			
			RespostaLinha respostalinha = new RespostaLinha(pergunta.get().getId(), respostaItem);
//			
//			
//			String respostaLinhaAsJson = new Gson().toJson(respostaItem);
//			
//			//return new RespostaDada(respostaLinha,pergunta.get());
//			RespostaDada respostaDada = new RespostaDada(pergunta.get(),respostaLinhaAsJson,respostaItem);
//			//gravar a Resposta dada
//			respostadadaRepository.save(respostaDada);

			// return new ResponseEntity<>("Hello World!", HttpStatus.OK) {;
			return ResponseEntity.ok(respostalinha);

		} else {
			System.out.println("Pergunta Não localizada!!! ");

			return new ResponseEntity<>("Pergunta não localizada!", HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return new ResponseEntity<>("Hello World!", HttpStatus.OK);
	}

	@GetMapping("/age")
	ResponseEntity<String> age(@RequestParam("yearOfBirth") int yearOfBirth) {

//	    if (isInFuture(yearOfBirth)) {
//	        return new ResponseEntity<>(
//	          "Year of birth cannot be in the future", 
//	          HttpStatus.BAD_REQUEST);
//	    }

		return new ResponseEntity<>("Your age is " + 123, HttpStatus.OK);
	}

	// EnviarArquivo
	@PostMapping("/multipla-resposta")
	public ResponseEntity<?> multiplaResposta(@RequestBody RespostaDadaMultiplaForm form,
			UriComponentsBuilder uriBuilder) {
		System.out.println("Entrou no Multiplo...");
		// Somente uma resposta por Pergunta

		// Converte o form na Pergunta
		RespostaDada respostaDada = form.converter(perguntaRepository);
		List<String> list = Arrays.asList("NAO", "SIM");

		RespostaLinha respostaLinha = new RespostaLinha(1, list);

		ObjectMapper mapper = new ObjectMapper();
		try {

			mapper.writeValue(new File("RespostaDada.json"), respostaLinha);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper2 = new ObjectMapper();
		RespostaLinha respostaLinha2 = null;
		try {
			respostaLinha2 = mapper.readValue("{\r\n" + "    \"idPergunta\": 1,\r\n" + "    \"respostaLinha\": [\r\n"
					+ "        \"GATO\",\r\n" + "        \"PATO\"\r\n" + "    ]\r\n" + "}", RespostaLinha.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(respostaLinha2);

		if (respostaDada == null) {
			System.out.println("Pergunta nula!");
			// return ResponseEntity.notFound().build();
			return null;
		}
		// Salva a Resposta
		respostadadaRepository.save(respostaDada);

		// Retorna
		URI uri = uriBuilder.path("/respostasdadas/{id}").buildAndExpand(respostaDada.getId()).toUri();
		return ResponseEntity.created(uri).body(respostaLinha2);
	}

}
