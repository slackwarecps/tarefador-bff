/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.modelo.tarefas;

/**
 * @author faapereira
 *
 */
public enum AcaoTipo {
	NENHUM(0), 
	ENCAMINHAR_PARA_LISTA(1);

	private int val;

	AcaoTipo(int value) {
		val = value;
	}

	public int getValue() {
		return val;
	}
}
