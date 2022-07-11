package br.com.fabioalvaro.tarefaFacil.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.fabioalvaro.tarefaFacil.modelo.forum.Usuario;
import br.com.fabioalvaro.tarefaFacil.repository.UsuarioRepository;

public class AutenticacaoViaTokenFilter  extends OncePerRequestFilter{
	
	private TokenService tokenService;
	private UsuarioRepository repository;
	
	

	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repository) {
		super();
		this.tokenService = tokenService;
		this.repository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//pegar o token
		String token = recuperarToken(request);
		//validar o token - verificar se é valido.
		//System.out.println("token:" + token);
		
		boolean valido = tokenService.isTokenValido(token);
		//System.out.println(valido);
		if (valido) {
			//forcar autorizacao pois esta ok
			autenticarCliente(token);
		}
		
		filterChain.doFilter(request, response);
		
	}

	private void autenticarCliente(String token) {
		//recuperar id do usuario
		Long idUsuario = tokenService.getIdUsuario(token);
		//carregar do banco de dados
		Usuario usuario = repository.findById(idUsuario).get();
		
		
		
		UsernamePasswordAuthenticationToken authetication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authetication);
	}


	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token ==null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7,token.length()) ;
	}

}
