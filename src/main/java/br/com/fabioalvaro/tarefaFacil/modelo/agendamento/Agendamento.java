/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.modelo.agendamento;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Lista;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Operador;

/**
 * @author faapereira
 *
 */
@Entity
public class Agendamento {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	@Enumerated(EnumType.STRING)
	private PeriodicidadeEnum periodicidade;
	private String horario;
	@Enumerated(EnumType.STRING)
	private AgendamentoStatusEnum status;
	@ManyToOne
	private Lista listaModelo;
	private Date dataCriacao;
	private Date dataUltimaExecucao;
	@Enumerated(EnumType.STRING)
	private AgendamentoTipoEnum tipoAgendamento;
	@ManyToMany
	private List<Operador> operadores;
	
	public Agendamento() {
		
	}
	
	
	public Agendamento(String descricao, PeriodicidadeEnum periodicidade, String horario,
			AgendamentoStatusEnum status, Lista listaModelo,
			AgendamentoTipoEnum tipoAgendamento, List<Operador> operadores) {

		this.descricao = descricao;
		this.periodicidade = periodicidade;
		this.horario = horario;
		this.status = status;
		this.listaModelo = listaModelo;
		this.dataCriacao = Calendar.getInstance().getTime();//Agora
		this.dataUltimaExecucao = null;
		this.tipoAgendamento = tipoAgendamento;
		this.operadores = operadores;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public PeriodicidadeEnum getPeriodicidade() {
		return periodicidade;
	}
	public void setPeriodicidade(PeriodicidadeEnum periodicidade) {
		this.periodicidade = periodicidade;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public AgendamentoStatusEnum getStatus() {
		return status;
	}
	public void setStatus(AgendamentoStatusEnum status) {
		this.status = status;
	}
	public Lista getListaModelo() {
		return listaModelo;
	}
	public void setListaModelo(Lista listaModelo) {
		this.listaModelo = listaModelo;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Date getDataUltimaExecucao() {
		return dataUltimaExecucao;
	}
	public void setDataUltimaExecucao(Date dataUltimaExecucao) {
		this.dataUltimaExecucao = dataUltimaExecucao;
	}
	public AgendamentoTipoEnum getTipoAgendamento() {
		return tipoAgendamento;
	}
	public void setTipoAgendamento(AgendamentoTipoEnum tipoAgendamento) {
		this.tipoAgendamento = tipoAgendamento;
	}
	public List<Operador> getOperadores() {
		return operadores;
	}
	public void setOperadores(List<Operador> operadores) {
		this.operadores = operadores;
	}
	
	
	

}
