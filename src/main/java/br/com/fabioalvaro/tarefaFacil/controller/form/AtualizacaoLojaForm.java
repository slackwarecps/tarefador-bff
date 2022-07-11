package br.com.fabioalvaro.tarefaFacil.controller.form;

import java.util.Optional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Loja;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Rede;
import br.com.fabioalvaro.tarefaFacil.repository.LojaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.RedeRepository;

public class AtualizacaoLojaForm {
	@NotNull @NotEmpty @Length(min=5)
	private String descricao;
	@NotNull
	@Min(1)
	private Long idRede;

	
	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Long getIdRede() {
		return idRede;
	}


	public void setIdRede(Long idRede) {
		this.idRede = idRede;
	}


	public Loja atualizar(Long id, LojaRepository lojaRepository, RedeRepository redeRepository) {
		
		
		Loja loja = lojaRepository.getOne(id);
		loja.setDescricao(this.descricao);
		
		//Busca a Rede
		Optional<Rede> rede = redeRepository.findById(this.idRede);
		if (rede.isPresent()) {
			loja.setRede(rede.get());
		}else {
			System.out.println("retornou null!!");
			return null;
		}
		
		
		
		
		return loja;
	}
}
