/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.com.fabioalvaro.tarefaFacil.modelo.agendamento.Agendamento;
import br.com.fabioalvaro.tarefaFacil.modelo.agendamento.AgendamentoStatusEnum;
import br.com.fabioalvaro.tarefaFacil.modelo.agendamento.AgendamentoTipoEnum;
import br.com.fabioalvaro.tarefaFacil.modelo.agendamento.PeriodicidadeEnum;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Lista;

/**
 * @author faapereira
 *
 */
public class DetalhesDoAgendamentoDto {
	private Long id;
	private String descricao;
	private PeriodicidadeEnum periodicidade;
	private String horario;
	private AgendamentoStatusEnum status;
	private Long listaModelo;
	private Date dataCriacao;
	private Date dataUltimaExecucao;
	private AgendamentoTipoEnum tipoAgendamento;
	private List<OperadorDto> listaOperadores;
	
	
	
	
	public DetalhesDoAgendamentoDto(Agendamento agendamento) {
		this.id = agendamento.getId();
		this.descricao = agendamento.getDescricao();
		this.periodicidade = agendamento.getPeriodicidade();
		this.horario = agendamento.getHorario();
		this.status = agendamento.getStatus();
		this.listaModelo = agendamento.getListaModelo().getId();
		this.dataCriacao = agendamento.getDataCriacao();
		this.dataUltimaExecucao = agendamento.getDataUltimaExecucao();
		this.tipoAgendamento = agendamento.getTipoAgendamento();
		
		List<OperadorDto> listaDeOperadores = agendamento.getOperadores().stream().map(OperadorDto::new).collect(Collectors.toList());
		this.listaOperadores=listaDeOperadores;		
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
	public Long getListaModelo() {
		return listaModelo;
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
	public List<OperadorDto> getOperadores() {
		return listaOperadores;
	}

	
	
	
	
	
}
