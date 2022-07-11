/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.dto;

import java.util.List;

import br.com.fabioalvaro.tarefaFacil.modelo.forum.Perfil;
import br.com.fabioalvaro.tarefaFacil.modelo.forum.Usuario;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Loja;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Rede;

/**
 * @author faapereira
 *
 */
public class DetalhesDoUsuarioDto {
	private Long id;
	private String nome;
	private String email;
	private List<Rede> redes;
	private List<Loja> lojas;
	private List<Perfil> perfis;
	
	
	public DetalhesDoUsuarioDto(Usuario usuario) {
		
		this.id =usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.redes = usuario.getRedes();
		this.lojas = usuario.getLojas();
		this.perfis = usuario.getPerfis();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}

	
	
	

	public List<Rede> getRedes() {
		return redes;
	}


	public void setRedes(List<Rede> redes) {
		this.redes = redes;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public List<Loja> getLojas() {
		return lojas;
	}


	public List<Perfil> getPerfis() {
		return perfis;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}
