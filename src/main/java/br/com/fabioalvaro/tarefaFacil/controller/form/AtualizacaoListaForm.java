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
import br.com.fabioalvaro.tarefaFacil.repository.ListaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.OperadorRepository;

/**
 * @author faapereira
 *
 */
public class AtualizacaoListaForm {
	@NotNull @NotEmpty @Length(min=5)
	private String descricao;
	@NotNull
	@Min(1)
	private Long idOperador;
	@NotNull @NotEmpty @Length(min=5)
	private String status;
	
	
	public Lista atualizar(Long id, ListaRepository listaRepository, OperadorRepository operadorRepository) {
		
		
		Lista lista = listaRepository.getOne(id);
		lista.setDescricao(this.descricao);
				
		//transforma a String no Enum e grava
		lista.setStatus(StatusLista.valueOf(status));
		
		//Busca o Operador FK
		Optional<Operador> operador = operadorRepository.findById(this.idOperador);
		if (operador.isPresent()) {
			lista.setOperador(operador.get());
		}else {
			System.out.println("retornou null!!");
			return null;
		}
		
		
		
		
		return lista;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
 	
}
