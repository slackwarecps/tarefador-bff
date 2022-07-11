package br.com.fabioalvaro.tarefaFacil.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fabioalvaro.tarefaFacil.controller.dto.DetalhesDaRedeDto;
import br.com.fabioalvaro.tarefaFacil.controller.dto.RedeDto;
import br.com.fabioalvaro.tarefaFacil.controller.form.AtualizacaoRedeForm;
import br.com.fabioalvaro.tarefaFacil.controller.form.RedeForm;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Rede;
import br.com.fabioalvaro.tarefaFacil.repository.RedeRepository;

@RestController
@RequestMapping("/redes")
public class RedesController {

	// Bean que acessa o banco de dados
	@Autowired
	private RedeRepository redeRepository;

	// BUSCA TODOS
	@GetMapping
	public Page<RedeDto> lista(@RequestParam(required = false) String descricao,
		@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		// Paginacao
		if (descricao == null) {
			Page<Rede> redes = redeRepository.findAll(paginacao);
			return RedeDto.converter(redes);
		} else {
			Page<Rede> redes = redeRepository.findByDescricao(descricao, paginacao);
			return RedeDto.converter(redes);
		}
	}

	// CADASTRAR
	@PostMapping
	public ResponseEntity<RedeDto> cadastrar(@RequestBody @Valid RedeForm form, UriComponentsBuilder uriBuilder) {

		// converte o form no topico
		Rede rede = form.converter();

		redeRepository.save(rede);

		URI uri = uriBuilder.path("/redes/{id}").buildAndExpand(rede.getId()).toUri();

		return ResponseEntity.created(uri).body(new RedeDto(rede));

	}

	// UPDATE
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<RedeDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoRedeForm form) {
		Optional<Rede> optional = redeRepository.findById(id);
		if (optional.isPresent()) {
			Rede rede = form.atualizar(id, redeRepository);
			return ResponseEntity.ok(new RedeDto(rede));
		}
		return ResponseEntity.notFound().build();

	}

	// DETALHAR POR ID
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDaRedeDto> detalhar(@PathVariable Long id) {

		Optional<Rede> rede = redeRepository.findById(id);
		if (rede.isPresent()) {
			return ResponseEntity.ok(new DetalhesDaRedeDto(rede.get()));
		}

		return ResponseEntity.notFound().build();
	}

	// DELETE
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "ListaDeTopicos", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Rede> optional = redeRepository.findById(id);
		if (optional.isPresent()) {
			redeRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}
