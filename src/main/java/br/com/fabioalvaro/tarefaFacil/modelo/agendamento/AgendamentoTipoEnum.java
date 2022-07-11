/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.modelo.agendamento;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author faapereira
 *
 */
public enum AgendamentoTipoEnum {
	LISTA_MODELO;
	
	public static boolean isValidAgendamentoTipo(String agendamentoTipo) {
		return Arrays.stream(AgendamentoTipoEnum.values()).map(AgendamentoTipoEnum::name).collect(Collectors.toSet())
				.contains(agendamentoTipo);
	}
}
