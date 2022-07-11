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
public enum AgendamentoStatusEnum {
	ATIVO,
	INATIVO;


	public static boolean isValidStatus(String status) {
		return Arrays.stream(AgendamentoStatusEnum.values()).map(AgendamentoStatusEnum::name).collect(Collectors.toSet())
				.contains(status);
	}

}
