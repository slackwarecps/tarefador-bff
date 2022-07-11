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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fabioalvaro.tarefaFacil.controller.dto.DetalhesDaLojaDto;
import br.com.fabioalvaro.tarefaFacil.controller.dto.LojaDto;
import br.com.fabioalvaro.tarefaFacil.controller.form.AtualizacaoLojaForm;
import br.com.fabioalvaro.tarefaFacil.controller.form.LojaForm;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Loja;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Rede;
import br.com.fabioalvaro.tarefaFacil.repository.LojaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.RedeRepository;

@RestController
@RequestMapping("/lojas")
public class LojasController {
	// Bean que acessa o banco de dados
	@Autowired
	private LojaRepository lojaRepository;
	@Autowired
	private RedeRepository redeRepository;
	
	// BUSCA  E RETORNA TODOS
	@GetMapping
	public Page<LojaDto> lista(@RequestParam(required = false) String descricao,
		@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		// Paginacao
		if (descricao == null) {
			Page<Loja> lojas = lojaRepository.findAll(paginacao);
			return LojaDto.converter(lojas);
		} else {
			Page<Loja> lojas = lojaRepository.findByDescricao(descricao, paginacao);
			return LojaDto.converter(lojas);
		}
	}
	

	// CADASTRAR
	@PostMapping

	public ResponseEntity<?> cadastrar(@RequestBody @Valid LojaForm form, UriComponentsBuilder uriBuilder) {

		// Converte o form no topico
		form.toString();
		Loja loja = form.converter(redeRepository);
		// loja.toString();

		if (loja == null) {
			System.out.println("loja nula!");
			// @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="was Not Found")
			return ResponseEntity.notFound().build();
		}

		// Salva o topico
		lojaRepository.save(loja);
		URI uri = uriBuilder.path("/lojas/{id}").buildAndExpand(loja.getId()).toUri();
		return ResponseEntity.created(uri).body(new LojaDto(loja));

	}


	
	
	// UPDATE
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<LojaDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoLojaForm form) {
		Optional<Loja> optional = lojaRepository.findById(id);
		if (optional.isPresent()) {
			Loja loja = form.atualizar(id, lojaRepository,redeRepository);
			
			if (loja == null) {
				System.out.println("loja nula!");
				return ResponseEntity.notFound().build();
			}
			
			return ResponseEntity.ok(new LojaDto(loja));
		}
		return ResponseEntity.notFound().build();

	}
	
	// DETALHAR POR ID
		@GetMapping("/{id}")
		public ResponseEntity<DetalhesDaLojaDto> detalhar(@PathVariable Long id) {
			System.out.println("chegou no detalhar loja....");
			Optional<Loja> loja = lojaRepository.findById(id);
			if (loja.isPresent()) {
				return ResponseEntity.ok(new DetalhesDaLojaDto(loja.get()));
			}

			return ResponseEntity.notFound().build();
		}
		

	// DELETE
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Loja> optional = lojaRepository.findById(id);
		if (optional.isPresent()) {
			lojaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	
	
	
	
	
	

}
