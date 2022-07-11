/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.form;

import java.util.Optional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Lista;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Operador;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.StatusLista;
import br.com.fabioalvaro.tarefaFacil.repository.OperadorRepository;

/**
 * Classe que recebe o form de cadastro
 * 
 * @author faapereira
 *
 */
public class ListaForm {
	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String descricao;
	@NotNull
	@Min(1)
	private Long idOperador;
	private StatusLista status = StatusLista.ATIVA;

	public Lista converter(OperadorRepository operadorRepository) {
		// vou procurar o operador pelo idOperador que veio no request
		Optional<Operador> operador = operadorRepository.findById(idOperador);

		if (operador.isPresent()) {
			// vou criar uma lista com a descricao que veio do request
			return new Lista(descricao, operador.get(),status);
		} else {
			return null;
		}
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getIdOperador() {
		return idOperador;
	}

	public void setIdOperador(Long idOperador) {
		this.idOperador = idOperador;
	}

	public StatusLista getStatus() {
		return status;
	}

	public void setStatus(StatusLista status) {
		this.status = status;
	}

}
