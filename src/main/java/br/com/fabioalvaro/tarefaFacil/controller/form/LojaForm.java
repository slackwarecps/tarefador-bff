package br.com.fabioalvaro.tarefaFacil.controller.form;

import java.util.Optional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Loja;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Rede;
import br.com.fabioalvaro.tarefaFacil.repository.RedeRepository;

public class LojaForm {
	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String descricao;
	@NotNull
	@Min(1)
	private Long idRede;

	
	// 
	public Loja converter(RedeRepository redeRepository) {
		Optional<Rede> rede = redeRepository.findById(idRede);
		if (rede.isPresent()) {
			return new Loja(descricao, rede.get());

		} else {
			System.out.println("retornou null!!");
			return null;
		}
	}

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

	@Override
	public String toString() {
		return "LojaForm [descricao=" + descricao + ", idRede=" + idRede + "]";
	}

}
