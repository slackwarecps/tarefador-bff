package br.com.fabioalvaro.tarefaFacil.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.tarefaFacil.config.security.token.PayloadToken;
import br.com.fabioalvaro.tarefaFacil.modelo.forum.Usuario;
import br.com.fabioalvaro.tarefaFacil.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${forum.jwt.expiration}")
	private String expiration;
	
	@Value("${forum.jwt.secret}")
	private String secret;
	
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public String gerarToken(Authentication authentication) {
		Usuario logado = (Usuario) authentication.getPrincipal();
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		
		//monta pyaload do jwt
		PayloadToken payloadToken = new PayloadToken();
		payloadToken.setIdUsuario(logado.getId());
		payloadToken.setIdade(41L);
		payloadToken.setSexo("MASCULINO");
		payloadToken.setUsuario(logado);
		
		System.out.println(logado);
		
		
		return Jwts.builder()
				.setIssuer("API do Fórum da Alura")
				.setSubject( payloadToken.toJson(payloadToken)  )
				
				.setIssuedAt(hoje)
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		
		String subject = claims.getSubject();
		//converter de volta para objeto
		PayloadToken payloadToken = new PayloadToken().converter(subject);
		
        return	Long.parseLong(	payloadToken.getIdUsuario().toString());
	}

}
