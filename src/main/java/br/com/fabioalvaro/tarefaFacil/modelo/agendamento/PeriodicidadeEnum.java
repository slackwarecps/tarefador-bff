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
public enum PeriodicidadeEnum {
	DIARIO, SEMANAL, MENSAL, ANUAL;
	
	
	
	
	

	public static boolean isValidPeriodicidade(final String periodicidade) {
		return Arrays.stream(PeriodicidadeEnum.values()).map(PeriodicidadeEnum::name).collect(Collectors.toSet())
				.contains(periodicidade);
	}

}
