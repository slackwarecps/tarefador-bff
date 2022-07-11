package br.com.fabioalvaro.tarefaFacil.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {
	private String email;
	private String senha;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public UsernamePasswordAuthenticationToken converter() {
		System.out.println(email);
		System.out.println(senha);
		
		return new UsernamePasswordAuthenticationToken(email,senha);

	}
	@Override
	public String toString() {
		return "LoginForm [email=" + email + ", senha=" + senha + "]";
	}
	
	
	
}
