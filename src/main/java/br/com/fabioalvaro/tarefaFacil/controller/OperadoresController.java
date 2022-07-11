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

import br.com.fabioalvaro.tarefaFacil.config.ErroDeFormularioDto;
import br.com.fabioalvaro.tarefaFacil.config.security.TokenService;
import br.com.fabioalvaro.tarefaFacil.controller.dto.DetalhesDoOperadorDto;
import br.com.fabioalvaro.tarefaFacil.controller.dto.OperadorDto;
import br.com.fabioalvaro.tarefaFacil.controller.form.AtualizacaoOperadorForm;
import br.com.fabioalvaro.tarefaFacil.controller.form.OperadorForm;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Lista;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Operador;
import br.com.fabioalvaro.tarefaFacil.repository.ListaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.LojaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.OperadorRepository;
import br.com.fabioalvaro.tarefaFacil.repository.UsuarioRepository;
import br.com.fabioalvaro.tarefaFacil.service.FiltroDoUsuarioService;
import br.com.fabioalvaro.tarefaFacil.utils.ErroPadrao;

@RestController
@RequestMapping("/operadores")
public class OperadoresController {
	// Bean que acessa o banco de dados
	@Autowired
	private OperadorRepository operadorRepository;
	@Autowired
	private LojaRepository lojaRepository;
	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ErroPadrao erroPadrao;

	@Autowired
	private FiltroDoUsuarioService filtroDoUsuario;
	@Autowired
	private ListaRepository listaRepository;
	

	
	
	// BUSCA TODOS
	@GetMapping
	public Page<OperadorDto> lista(
			@RequestHeader("Authorization") String authorizationHeader,
			@RequestParam(required = false) String nome,
			@RequestParam(required = false) Long idRede,
			@RequestParam(required = false) Long idLoja,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 50) Pageable paginacao) {

		// redes e lojas autorizadas para essa request
		filtroDoUsuario.FiltroDoUsuario(authorizationHeader, tokenService, usuarioRepository);
		List<Integer> filtroDeRede = filtroDoUsuario.getFiltroDeRede();
		List<Integer> filtroDeLoja = filtroDoUsuario.getFiltroDeLoja();


		
		// se voce nao pode buscar te dou um erro pra voce ir embora
		if (idRede != null && (!filtroDeRede.contains(idRede.intValue()))) {
			filtroDeRede = filtroDoUsuario.invalidaFiltro();
		}

		if (idLoja != null && (!filtroDeLoja.contains(idLoja.intValue()))) {
			System.out.println("comparando parametros");
			filtroDeLoja = filtroDoUsuario.invalidaFiltro();
		}

		Page<Operador> operadores;
		System.out.println(filtroDeRede);
		System.out.println(filtroDeLoja);
		operadores = operadorRepository.findAllFiltrado(filtroDeRede, filtroDeLoja, paginacao);
		return OperadorDto.converter(operadores);

	}

	// CADASTRAR
	@PostMapping
	public ResponseEntity<?> cadastrar(
			@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody @Valid OperadorForm form, UriComponentsBuilder uriBuilder) {
		erroPadrao.start();
		// Converte o form no Operador
		form.toString();
		
		
		//PERMISSOES		
		Long idLoja = form.getIdLoja();
				
		// Essa lista é de uma rede que voce tem autorizacao para buscar?
		filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
		if (filtroDoUsuario.isPermitidoAcessarLoja(lojaRepository, idLoja) == false) {
		
			erroPadrao.addError(new ErroDeFormularioDto("idLoja", "Você não pode cadastrar operadores nesta loja. #Permissoes"));
			
		}
		
		
		
		if (erroPadrao.hasError() == true) {
			return erroPadrao.response();
		}
		
		
		// injeto o foreing no form pra buscar o fk
		Operador operador = form.converter(lojaRepository);
		if (operador == null) {
			System.out.println("Operador null!");
			return ResponseEntity.notFound().build();
		}
		
		

		
		
		
	
		
		// Salva o Operador
		operadorRepository.save(operador);
		URI uri = uriBuilder.path("/operadores/{id}").buildAndExpand(operador.getId()).toUri();
		return ResponseEntity.created(uri).body(new OperadorDto(operador));
	}

	// UPDATE
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> atualizar(
			@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id,
			@RequestBody @Valid AtualizacaoOperadorForm form) {
		erroPadrao.start();
		Optional<Operador> optional = operadorRepository.findById(id);
		
		
		
		
		
		if (optional.isPresent()) {
			Operador operador = form.atualizar(id, operadorRepository, lojaRepository);
			
			
			
			//PERMISSOES		
			Long idLoja = form.getIdLoja();
					
			// Essa lista é de uma rede que voce tem autorizacao para buscar?
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			if (filtroDoUsuario.isPermitidoAcessarLoja(lojaRepository, idLoja) == false) {
			
				erroPadrao.addError(new ErroDeFormularioDto("idLoja", "Você não pode atualizar operadores nesta loja. #Permissoes"));
				
			}
			
			
			
			if (erroPadrao.hasError() == true) {
				return erroPadrao.response();
			}
			

			if (operador == null) {
				System.out.println("Operador null!");
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok(new OperadorDto(operador));
		}
		return ResponseEntity.notFound().build();

	}

	// DETALHAR POR ID
	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(
			@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id) {
		erroPadrao.start();
		Optional<Operador> operador = operadorRepository.findById(id);
		if (operador.isPresent()) {
			
			// Permissões
			// Essa lista é de uma rede que voce tem autorizacao para buscar?
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			if (!filtroDoUsuario.isOperadorPermitido(operadorRepository,id)){
			
				erroPadrao.addError(new ErroDeFormularioDto("id","Você não pode detalhar esse Operador. #Permissoes"));
			}
			if (erroPadrao.hasError() == true) {
				return erroPadrao.response();
			}
			// Fim Permissões
			
			return ResponseEntity.ok(new DetalhesDoOperadorDto(operador.get()));
		}
		return ResponseEntity.notFound().build();
	}

	// DELETE
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id) {
		erroPadrao.start();
		// Permissões		
		filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
		if (!filtroDoUsuario.isOperadorPermitido(operadorRepository,id)){
			System.out.println("Sim é permitido.... apagar o "+ id);
			erroPadrao.addError(new ErroDeFormularioDto("id","Você não pode excluir esse Operador. #Permissoes"));
		}
		if (erroPadrao.hasError() == true) {
			return erroPadrao.response();
		}
		// Fim Permissões
		
		
		//Existem Listas para esse operador?

		Long listas = listaRepository.countByOperador_Id(id);
		if (listas >0) {
		
			erroPadrao.addError(new ErroDeFormularioDto("idLista","Você não pode excluir esse Operador. Pois ele esta associado a uma lista."));
		}
		if (erroPadrao.hasError() == true) {
			return erroPadrao.response();
		}
		
		
		Optional<Operador> optional = operadorRepository.findById(id);
		if (optional.isPresent()) {
			//operadorRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}
