package br.com.fabioalvaro.tarefaFacil.controller.form;

import java.util.Optional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Loja;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Operador;
import br.com.fabioalvaro.tarefaFacil.repository.LojaRepository;

public class OperadorForm {
	@NotNull	@NotEmpty 	@Length(min = 5)
	private String nome;
	@NotNull
	@Min(1)@Max(999)
	private Long idLoja;
	private Long idUsuario;
	
	



	public Operador converter(LojaRepository lojaRepository) {
		//Buscando o Foreign 
		Optional<Loja> loja = lojaRepository.findById(idLoja);
		if (loja.isPresent()) {
			return new Operador (nome,loja.get(),idUsuario);
			
		}else {
			System.out.println("retornou null!!");
			return null;
		}
	}
	
	
	public Long getIdUsuario() {
		return idUsuario;
	}





	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}





	public String getNome() {
		return nome;
	}





	public void setNome(String nome) {
		this.nome = nome;
	}





	public Long getIdLoja() {
		return idLoja;
	}





	public void setIdLoja(Long idLoja) {
		this.idLoja = idLoja;
	}




}
