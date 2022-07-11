/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.fabioalvaro.tarefaFacil.modelo.agendamento.Agendamento;
import br.com.fabioalvaro.tarefaFacil.modelo.agendamento.AgendamentoStatusEnum;
import br.com.fabioalvaro.tarefaFacil.modelo.agendamento.AgendamentoTipoEnum;
import br.com.fabioalvaro.tarefaFacil.modelo.agendamento.PeriodicidadeEnum;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Lista;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Operador;
import br.com.fabioalvaro.tarefaFacil.repository.AgendamentoRepository;
import br.com.fabioalvaro.tarefaFacil.repository.ListaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.OperadorRepository;

/**
 * @author faapereira
 *
 */
public class AtualizacaoAgendamentoForm {
	@NotNull
	@Min(1)
	private Long idListaModelo;
	@NotNull
	@NotEmpty
	@Length(min = 3)
	private String descricao;
	private List<Long> listaDeOperadores;
	private String horario;

	private String periodicidade;
	private String status;
	private String tipoAgendamento;

	public Agendamento atualizar(Long id, AgendamentoRepository agendamentoRepository, ListaRepository listaRepository, OperadorRepository operadorRepository) {
		Agendamento agendamento = agendamentoRepository.getOne(id);
		agendamento.setDescricao(this.descricao);
		
		//Converter Lista
	
		Optional<Lista> listaModelo = listaRepository.findById(this.idListaModelo);
		if (listaModelo.isPresent()) {
			System.out.println("ok achou a lista");
			
		} else {
			System.out.println("Lista Vinculada - Não Achou!!! NULL");
			return null;
		}
				
		agendamento.setListaModelo(listaModelo.get());
		agendamento.setStatus(AgendamentoStatusEnum.valueOf(this.status));
		agendamento.setPeriodicidade(PeriodicidadeEnum.valueOf(this.periodicidade));
		agendamento.setTipoAgendamento(AgendamentoTipoEnum.valueOf(this.tipoAgendamento));
		agendamento.setHorario(this.horario);
		
		//Converter Operadores
		//List<Operador> ListaDeOperadoresAtualizada = operadorRepository.findAll();
		//Insere a Lista de Operadores		
		List<Operador> listaRecebidaOperadores= new ArrayList<>();
		listaDeOperadores.forEach(operador->{			
			Optional<Operador> operadorLocalizado = operadorRepository.findById(operador);
			if (operadorLocalizado.isPresent()) {
				listaRecebidaOperadores.add(operadorLocalizado.get());
			}
		});
		
		agendamento.setOperadores(listaRecebidaOperadores);
		
		
		return agendamento;
	}

	public Long getIdListaModelo() {
		return idListaModelo;
	}

	public void setIdListaModelo(Long idListaModelo) {
		this.idListaModelo = idListaModelo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Long> getOperadores() {
		return listaDeOperadores;
	}

	public void setOperadores(List<Long> operadores) {
		this.listaDeOperadores = operadores;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getPeriodicidade() {
		return periodicidade;
	}

	public void setPeriodicidade(String periodicidade) {
		this.periodicidade = periodicidade;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTipoAgendamento() {
		return tipoAgendamento;
	}

	public void setTipoAgendamento(String tipoAgendamento) {
		this.tipoAgendamento = tipoAgendamento;
	}

}
