package br.com.fabioalvaro.tarefaFacil.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabioalvaro.tarefaFacil.config.security.TokenService;
import br.com.fabioalvaro.tarefaFacil.controller.dto.TokenDto;
import br.com.fabioalvaro.tarefaFacil.controller.form.LoginForm;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {
		// manda o email e a senha pra se transformar em uma objeto
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		
		System.out.println("Dados Login");
		System.out.println(form);
		
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			System.out.println(dadosLogin);
		} catch (Exception e) {
			System.out.println("Erro ao autenticar os dados do login ruim...");
			System.out.println(dadosLogin);
			return ResponseEntity.badRequest().build();
		}
		
		

		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);
			
			return ResponseEntity.ok(new TokenDto(token,"Bearer"));

		} catch (AuthenticationException e) {
			System.out.println("deu ruim...");
			return ResponseEntity.badRequest().build();
		}

	}
}
