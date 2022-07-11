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
import br.com.fabioalvaro.tarefaFacil.repository.OperadorRepository;

public class AtualizacaoOperadorForm {
	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String nome;
	@NotNull
	@Min(1)
	@Max(999)
	private Long idLoja;

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

	public Operador atualizar(Long id, OperadorRepository operadorRepository, LojaRepository lojaRepository) {

		Operador operador = operadorRepository.getOne(id);
		operador.setNome(this.nome);

		// Busca o Foreing Objeto
		Optional<Loja> loja = lojaRepository.findById(this.idLoja);
		if (loja.isPresent()) {
			operador.setLoja(loja.get());
		} else {
			System.out.println("retornou null!!");
			return null;
		}

		return operador;
	}
}
