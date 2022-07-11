/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.commons;

/**
 * @author faapereira
 *
 */
public enum MensagemErroEnum {
	FORMATO_INVALIDO(4000,"Formato Inválido"), 
	VALOR_INVALIDO(  4001,"Valor Inválido!!"),
	LISTA_DE_OPERADORES_INVALIDA(4002,"Lista de operadores inválida!");

	

	private Integer codigo;

	private String mensagemErro;


	MensagemErroEnum(Integer codigo, String mensagemErro) {
		this.codigo = codigo;
		this.mensagemErro = mensagemErro;

	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getMensagemErro() {
		return mensagemErro;
	}



}
