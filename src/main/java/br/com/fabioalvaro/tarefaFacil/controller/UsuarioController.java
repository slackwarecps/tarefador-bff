/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabioalvaro.tarefaFacil.controller.dto.DetalhesDoUsuarioDto;
import br.com.fabioalvaro.tarefaFacil.modelo.forum.Usuario;
import br.com.fabioalvaro.tarefaFacil.repository.UsuarioRepository;

/**
 * @author faapereira
 *
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	// Bean que acessa o banco de dados
	@Autowired
	private UsuarioRepository usuarioRepository;
	// DETALHAR POR ID
	@GetMapping("/detalhePorEmail/{email}")
	public ResponseEntity<DetalhesDoUsuarioDto> detalharPorEmail(@PathVariable String email) {
		//System.out.println("chegou no detalhar loja....");
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		if (usuario.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoUsuarioDto(usuario.get()));
		}

		return ResponseEntity.notFound().build();
	}
	
}
