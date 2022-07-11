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
import br.com.fabioalvaro.tarefaFacil.repository.ListaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.OperadorRepository;

/**
 * @author faapereira
 *
 */
public class AgendamentoForm {
	@NotNull
	@Min(1)
	private Long idListaModelo;	
	@NotNull
	@NotEmpty
	@Length(min = 3)
	private String descricao;
	private List<Long> operadores;
	private String horario;
	
	private String periodicidade;
	private String status;
	private String tipoAgendamento;	
	
	
	
	
	
	/**
	 * @return
	 */
	public Agendamento converter(ListaRepository listaRepository, OperadorRepository operadorRepository) {
		PeriodicidadeEnum periodicidadeRec= PeriodicidadeEnum.valueOf(periodicidade);
		AgendamentoStatusEnum statusRec= AgendamentoStatusEnum.valueOf(status);
		AgendamentoTipoEnum tipoRec= AgendamentoTipoEnum.valueOf(tipoAgendamento);
		
		//busco no banco as FKS se preciso for 
		Optional<Lista> lista = listaRepository.findById(idListaModelo);
		if (lista.isPresent()) {
			System.out.println("ok achou a lista");
			
		} else {
			System.out.println("Lista Vinculada - Não Achou!!! NULL");
			return null;
		}
	
		//Insere a Lista de Operadores		
		List<Operador> listaRecebidaOperadores= new ArrayList<>();
		operadores.forEach(operador->{			
			Optional<Operador> operadorLocalizado = operadorRepository.findById(operador);
			if (operadorLocalizado.isPresent()) {
				listaRecebidaOperadores.add(operadorLocalizado.get());
			}
		});
				
		return new Agendamento(descricao,periodicidadeRec,horario,statusRec,lista.get(),tipoRec,listaRecebidaOperadores);
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
		return operadores;
	}





	public void setOperadores(List<Long> operadores) {
		this.operadores = operadores;
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





	@Override
	public String toString() {
		return "AgendamentoForm [idListaModelo=" + idListaModelo + ", descricao=" + descricao + ", operadores="
				+ operadores + ", horario=" + horario + ", periodicidade=" + periodicidade + ", status=" + status
				+ ", tipoAgendamento=" + tipoAgendamento + "]";
	}
	
	
	
	
	
	
}
