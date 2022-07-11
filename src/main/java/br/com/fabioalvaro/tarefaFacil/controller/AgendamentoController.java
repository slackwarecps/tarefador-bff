/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller;

import java.net.URI;
import java.util.ArrayList;
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

import br.com.fabioalvaro.tarefaFacil.commons.MensagemErroEnum;
import br.com.fabioalvaro.tarefaFacil.commons.Time24HoursValidator;
import br.com.fabioalvaro.tarefaFacil.config.ErroDeFormularioDto;
import br.com.fabioalvaro.tarefaFacil.config.security.TokenService;
import br.com.fabioalvaro.tarefaFacil.controller.dto.AgendamentoDto;
import br.com.fabioalvaro.tarefaFacil.controller.dto.DetalhesDoAgendamentoDto;
import br.com.fabioalvaro.tarefaFacil.controller.form.AgendamentoForm;
import br.com.fabioalvaro.tarefaFacil.controller.form.AtualizacaoAgendamentoForm;
import br.com.fabioalvaro.tarefaFacil.modelo.agendamento.Agendamento;
import br.com.fabioalvaro.tarefaFacil.modelo.agendamento.AgendamentoStatusEnum;
import br.com.fabioalvaro.tarefaFacil.modelo.agendamento.AgendamentoTipoEnum;
import br.com.fabioalvaro.tarefaFacil.modelo.agendamento.PeriodicidadeEnum;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Lista;
import br.com.fabioalvaro.tarefaFacil.repository.AgendamentoRepository;
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
@RequestMapping("/agendamentos")
public class AgendamentoController {
	@Autowired
	private AgendamentoRepository agendamentoRepository;
	@Autowired
	private ListaRepository listaRepository;
	@Autowired
	private OperadorRepository operadorRepository;
	@Autowired
	private ErroPadrao erroPadrao;

	private Time24HoursValidator time24HoursValidator = new Time24HoursValidator();

	@Autowired
	private FiltroDoUsuarioService filtroDoUsuario;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UsuarioRepository usuarioRepository;

	/**
	 * BUSCA E RETORNA TODOS
	 */
	@GetMapping
	public Page<AgendamentoDto> lista(@RequestHeader("Authorization") String authorizationHeader,
			@RequestParam(required = false) String descricao, @RequestParam(required = false) Long idRede,
			@RequestParam(required = false) Long idLoja, @RequestParam(required = false) String statusAgendamento,
			@RequestParam(required = false) Long idOperador,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 50) Pageable paginacao) {

		// redes e lojas autorizadas para essa request
		filtroDoUsuario.FiltroDoUsuario(authorizationHeader, tokenService, usuarioRepository);
		List<Integer> filtroDeRede = filtroDoUsuario.getFiltroDeRede();
		List<Integer> filtroDeLoja = filtroDoUsuario.getFiltroDeLoja();
		List<String> filtroDeStatus = new ArrayList<>();
		List<Integer> filtroDeOperador = new ArrayList<>();

		// se voce nao pode buscar te dou um erro pra voce ir embora
		if (idRede != null && (!filtroDeRede.contains(idRede.intValue()))) {
			filtroDeRede = filtroDoUsuario.invalidaFiltro();
		}

		if (idLoja != null && (!filtroDeLoja.contains(idLoja.intValue()))) {
			
			filtroDeLoja = filtroDoUsuario.invalidaFiltro();
		}
		if (idOperador != null) {
			
			filtroDeOperador.add(idOperador.intValue());
		}
		Page<Agendamento> agendamentos;

		if (statusAgendamento == null && idOperador == null) {
			
			agendamentos = agendamentoRepository.findAllFiltrado(filtroDeRede, filtroDeLoja, paginacao);
		} else if (statusAgendamento != null && idOperador == null) {
			
			filtroDeStatus.add(statusAgendamento);
			agendamentos = agendamentoRepository.findAllFiltradoPorStatus(filtroDeRede, filtroDeLoja, filtroDeStatus,
					paginacao);
		} else if (statusAgendamento == null && idOperador != null) {
			
			filtroDeOperador.add(idOperador.intValue());
			agendamentos = agendamentoRepository.findAllFiltradoOperador(filtroDeRede, filtroDeLoja, filtroDeOperador,
					paginacao);

		} else {
			// tudo
			System.out.println("COM STATUS e COM operador");
			filtroDeStatus.add(statusAgendamento);
			agendamentos = agendamentoRepository.findAllFiltrado(filtroDeRede, filtroDeLoja, filtroDeOperador,
					filtroDeStatus, paginacao);
			// return AgendamentoDto.converter(agendamentos);
		}

		return AgendamentoDto.converter(agendamentos);
	}

	// CADASTRAR
	@PostMapping(produces = { "application/json" })
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody @Valid AgendamentoForm form, UriComponentsBuilder uriBuilder) {
		erroPadrao.start();

		filtroDoUsuario.FiltroDoUsuario(authorizationHeader, tokenService, usuarioRepository);

		// Validacoes
		validarAgendamento(form, erroPadrao);

		// Validação de Permissões Operadores
		validaOperadores(form.getOperadores());

		if (erroPadrao.hasError() == true) {
			return erroPadrao.response();
		}

		// Fim Permissões de usuario

		// Converte o form no topico
		Agendamento agendamento = form.converter(listaRepository, operadorRepository);
		// Permissões
		// Validando Lista Vinculada
		Optional<Lista> listaLocalizada = listaRepository.findById(form.getIdListaModelo());
		if (!listaLocalizada.isPresent()) {
			return erroPadrao.badRequest("idListaVinculada", "Lista Vinculada não Localizada.");
		}
		Long idListaVinculada = form.getIdListaModelo();
		// Esse agendamento é de uma rede que voce tem autorizacao para buscar?
		filtroDoUsuario.FiltroDoUsuario(authorizationHeader, tokenService, usuarioRepository);

		if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository, idListaVinculada)) {
			return erroPadrao.badRequest("idListaModelo", "Você não tem acesso a esta lista. #Permissoes");
		}

		// Fim Permissões

		agendamentoRepository.save(agendamento);
		URI uri = uriBuilder.path("/agendamentos/{id}").buildAndExpand(agendamento.getId()).toUri();
		return ResponseEntity.created(uri).body(new AgendamentoDto(agendamento));

	}

	/**
	 * @param form
	 */
	private void validaOperadores(List<Long> listaOperadores) {
		listaOperadores.forEach(item -> {
			System.out.println(item.toString());
			Long idOperador = item;

			Boolean operadorPermitido = filtroDoUsuario.isOperadorPermitido(operadorRepository, idOperador);
			if (operadorPermitido == false) {
				erroPadrao.addError(new ErroDeFormularioDto("operadores","voce nao tem acesso ao operador " + item + ". #Permissoes "));

			}

		});
	}

	// UPDATE
	@PutMapping("/{id}")
	@Transactional
	@PostMapping(produces = { "application/json" })
	public ResponseEntity<?> atualizar(@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id, @RequestBody @Valid AtualizacaoAgendamentoForm form) {
		erroPadrao.start();
		Optional<Agendamento> agendamentoLocalizada = agendamentoRepository.findById(id);
		if (agendamentoLocalizada.isPresent()) {
			
			
			// Permissões
			// Essa lista é de uma rede que voce tem autorizacao para buscar?
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			if (!filtroDoUsuario.isPermitidoAcessarLista(listaRepository,form.getIdListaModelo())) {
				//return erroPadrao.badRequest("idListaModelo", "Você não tem acesso a essa lista modelo. #Permissoes");
				erroPadrao.addError(new ErroDeFormularioDto("idListaModelo","Você não tem acesso a essa lista modelo. #Permissoes "));
			}
			
			
			// Validação de Permissões Operadores
			validaOperadores(form.getOperadores());

			
			
			if (erroPadrao.hasError() == true) {
				return erroPadrao.response();
			}
			// Fim Permissões
			
			
			
			
			Agendamento agendamento = form.atualizar(id, agendamentoRepository, listaRepository, operadorRepository);
			if (agendamento == null) {
				System.out.println("pergunta null!");
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(new AgendamentoDto(agendamento));
		}
		return ResponseEntity.notFound().build();

	}

	// DETALHAR POR ID
	@GetMapping("/{id}")
	@PostMapping(produces = { "application/json" })
	public ResponseEntity<?> detalhar(@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id) {
		erroPadrao.start();
		Optional<Agendamento> agendamento = agendamentoRepository.findById(id);
		if (agendamento.isPresent()) {
			
			// Validação de Permissões
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			Boolean operadorPermitido = filtroDoUsuario.isUsuarioPermitidoApagarAgendamento(agendamentoRepository, id);
			if (operadorPermitido == false) {
				
				erroPadrao.addError(new ErroDeFormularioDto("idListaModelo","Você não pode detalhar esse agendamento. #Permissoes"));
			}
			if (erroPadrao.hasError() == true) {
				return erroPadrao.response();
			}
			//Fim Permissões	
			
			

			return ResponseEntity.ok(new DetalhesDoAgendamentoDto(agendamento.get()));
		}

		return ResponseEntity.notFound().build();
	}

	// DELETE
	@DeleteMapping("/{id}")
	@PostMapping(produces = { "application/json" })
	@Transactional
	public ResponseEntity<?> remover(@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable Long id) {
		Optional<Agendamento> agendamentoLocalizada = agendamentoRepository.findById(id);
		erroPadrao.start();
		if (agendamentoLocalizada.isPresent()) {

			
			// Validação de Permissões
			filtroDoUsuario.FiltroDoUsuario(authorizationHeader,tokenService,usuarioRepository);
			Boolean operadorPermitido = filtroDoUsuario.isUsuarioPermitidoApagarAgendamento(agendamentoRepository, id);
			if (operadorPermitido == false) {
				//return erroPadrao.badRequest("id", "Você não pode apagar esse agendamento. #Permissoes");
				erroPadrao.addError(new ErroDeFormularioDto("idListaModelo","Você não pode apagar esse agendamento. #Permissoes"));
			}
			if (erroPadrao.hasError() == true) {
				return erroPadrao.response();
			}
			//Fim Permissões			
			
			
			agendamentoRepository.deleteById(id);

			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

	/**
	 * @param form
	 * @param response
	 */
	private void validarAgendamento(@Valid AgendamentoForm form, ErroPadrao erroPadrao) {

		if (!time24HoursValidator.validate(form.getHorario())) {

			erroPadrao.addError(new ErroDeFormularioDto("periodicidade", "Horario inválido"));
			return;
		}

		if (!PeriodicidadeEnum.isValidPeriodicidade(form.getPeriodicidade())) {

			erroPadrao.addError(
					new ErroDeFormularioDto("periodicidade", MensagemErroEnum.VALOR_INVALIDO.getMensagemErro()));
			return;
		}

		if (!AgendamentoStatusEnum.isValidStatus(form.getStatus())) {
			erroPadrao.addError(new ErroDeFormularioDto("status", MensagemErroEnum.VALOR_INVALIDO.getMensagemErro()));
			return;
		}

		if (!AgendamentoTipoEnum.isValidAgendamentoTipo(form.getTipoAgendamento())) {
			erroPadrao.addError(
					new ErroDeFormularioDto("tipoAgendamento", MensagemErroEnum.VALOR_INVALIDO.getMensagemErro()));
			return;
		}

		// Valida Operadores se existem!
		List<Long> listaOperadores = form.getOperadores();
		Integer operadoresContador = operadorRepository.countById(listaOperadores);
		if (operadoresContador != listaOperadores.size()) {
			erroPadrao.addError(new ErroDeFormularioDto("operadores",
					MensagemErroEnum.LISTA_DE_OPERADORES_INVALIDA.getMensagemErro()));
			return;
		}

		// Valida Lista Existe

		Optional<Lista> listaModelo = listaRepository.findById(form.getIdListaModelo());
		if (!listaModelo.isPresent()) {
			erroPadrao.addError(new ErroDeFormularioDto("IdListaModelo", "Lista Modelo não encontrada!"));
			return;
		}
		if (operadoresContador != listaOperadores.size()) {

		}

	}

}
