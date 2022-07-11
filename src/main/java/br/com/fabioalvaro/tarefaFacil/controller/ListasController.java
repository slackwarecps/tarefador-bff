/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import br.com.fabioalvaro.tarefaFacil.controller.dto.DetalhesDaListaDto;
import br.com.fabioalvaro.tarefaFacil.controller.dto.ListaDto;
import br.com.fabioalvaro.tarefaFacil.controller.form.AtualizacaoListaForm;
import br.com.fabioalvaro.tarefaFacil.controller.form.ListaForm;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Lista;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.StatusLista;
import br.com.fabioalvaro.tarefaFacil.repository.ListaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.OperadorRepository;
import br.com.fabioalvaro.tarefaFacil.repository.UsuarioRepository;
import br.com.fabioalvaro.tarefaFacil.service.FiltroDoUsuarioService;
import br.com.fabioalvaro.tarefaFacil.utils.ErroPadrao;

/**
 * @author faapereira
 *
 */
@RestController
@RequestMapping("/listas")
public class ListasController {

	@Autowired
	private ListaRepository listaRepository;

	@Autowired
	private OperadorRepository operadorRepository;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ErroPadrao erroPadrao;

	@Autowired
	private FiltroDoUsuarioService filtroDoUsuario;


	/**
	 * BUSCA E RETORNA TODOS
	 */
	@GetMapping
	public Page<ListaDto> lista(
			@RequestHeader("Authorization") String authorizationHeader,			
			@RequestParam(required = false) String descricao,
			@RequestParam(required = false) Long idRede, 
			@RequestParam(required = false) Long idLoja,
			@RequestParam(required = false) String statusLista, 
			@RequestParam(required = false) Long idOperador,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 50) Pageable paginacao) {

		// redes e lojas autorizadas para essa request
		filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);		
		List<Integer> filtroDeRede = filtroDoUsuario.getFiltroDeRede();
		List<Integer> filtroDeLoja = filtroDoUsuario.getFiltroDeLoja();
		List<String> filtroDeStatus = new ArrayList<>();
		List<Integer> filtroDeOperador = new ArrayList<>();
		
		// se voce nao pode buscar te dou um erro pra voce ir embora
		if (idRede != null && (!filtroDeRede.contains(idRede.intValue()))) {
			filtroDeRede = filtroDoUsuario.invalidaFiltro();
		}

		if (idLoja != null && (!filtroDeLoja.contains(idLoja.intValue()))) {
			System.out.println("comparando parametros");
			filtroDeLoja = filtroDoUsuario.invalidaFiltro();
		}
		if (idOperador != null) {
			System.out.println("comparando parametros");
			filtroDeOperador.add(idOperador.intValue());
		}
		Page<Lista> listas;

		if (statusLista == null && idOperador == null) {
			System.out.println("SEM STATUS e SEM operador");
			listas = listaRepository.findAllFiltrado(filtroDeRede, filtroDeLoja, paginacao);
		} else if (statusLista != null && idOperador == null) {
			System.out.println("COM STATUS e sem operador");
			filtroDeStatus.add(statusLista);
			listas = listaRepository.findAllFiltradoPorStatus(filtroDeRede, filtroDeLoja, filtroDeStatus, paginacao);
		} else if (statusLista == null && idOperador != null) {
			System.out.println("SEM STATUS e COM operador");
			filtroDeOperador.add(idOperador.intValue());
			listas = listaRepository.findAllFiltradoOperador(filtroDeRede, filtroDeLoja, filtroDeOperador, paginacao);

		} else {
			// tudo
			System.out.println("COM STATUS e COM operador");
			filtroDeStatus.add(statusLista);
			listas = listaRepository.findAllFiltrado(filtroDeRede, filtroDeLoja, filtroDeOperador, filtroDeStatus,paginacao);
			// return ListaDto.converter(listas);

		}
		return ListaDto.converter(listas);

	}

	// CADASTRAR
	@PostMapping

	public ResponseEntity<?> cadastrar(@RequestBody @Valid ListaForm form, UriComponentsBuilder uriBuilder) {

		// Converte o form na Lista
		form.toString();
		Lista lista = form.converter(operadorRepository);
		if (lista == null) {
			return ResponseEntity.notFound().build();
		}

		// Salva o topico
		listaRepository.save(lista);
		URI uri = uriBuilder.path("/listas/{id}").buildAndExpand(lista.getId()).toUri();
		return ResponseEntity.created(uri).body(new ListaDto(lista));

	}

	// UPDATE
	@PutMapping("/{id}")
	@PostMapping(produces = { "application/json" })
	@Transactional
	public ResponseEntity<?> atualizar(@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id, @RequestBody @Valid AtualizacaoListaForm form) {

		// status é valido?
		// Validando STATUS
		if (form.getStatus() == null) {
			return erroPadrao.badRequest("status", "Status deve ser preenchido corretamente");
		} else {
			try {
				StatusLista statusRecebido = StatusLista.valueOf(form.getStatus());
				System.out.println(statusRecebido);
			} catch (IllegalArgumentException ex) {
				return erroPadrao.badRequest("status",
						"V:O status informado não é permitido padrão é ATIVA,INATIVA,FINALIZADA");
			}
		}

		// Validação de Permissões
		filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
		Boolean operadorPermitido = filtroDoUsuario.isOperadorPermitido(operadorRepository, form.getIdOperador());
		if (operadorPermitido == false) {
		return erroPadrao.badRequest("status", "Você não pode atualizar essa lista. #Permissoes");
		}
		//Fim Permissões

		Optional<Lista> optional = listaRepository.findById(id);
		if (optional.isPresent()) {
			Lista lista = form.atualizar(id, listaRepository, operadorRepository);

			if (lista == null) {
				System.out.println("operador null!");
				return ResponseEntity.notFound().build();
			}
			
			
			

			return ResponseEntity.ok(new ListaDto(lista));
		}
		return ResponseEntity.notFound().build();

	}

	// DETALHAR POR ID
	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(
			@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id) {
		// voce pode apagar essa lista?

		Optional<Lista> lista = listaRepository.findById(id);
		if (lista.isPresent()) {
			
			// Validação de Permissões
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			Boolean operadorPermitido = filtroDoUsuario.isOperadorPermitidoApagarLista(listaRepository, id);
			if (operadorPermitido == false) {
				return erroPadrao.badRequest("id", "Você não pode ver detalhes desta lista. #Permissoes");
			}
			//Fim Permissões
			
			
			
			return ResponseEntity.ok(new DetalhesDaListaDto(lista.get()));
		}

		return ResponseEntity.notFound().build();
	}

	
	
	
	
	
	// DELETE LISTA
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(
			@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id) {
		// Permissoes do Usuario
		
		Optional<Lista> optional = listaRepository.findById(id);
		if (optional.isPresent()) {
		
			// Validação de Permissões
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			Boolean operadorPermitido = filtroDoUsuario.isOperadorPermitidoApagarLista(listaRepository, id);
			if (operadorPermitido == false) {
				return erroPadrao.badRequest("id", "Você não pode apagar essa lista. #Permissoes");
			}
			//Fim Permissões

			// ok apagando a lista
			
			listaRepository.deleteById(id);
			
			
			
			
			
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	
	
	
	
	
	
	

}
