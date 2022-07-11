/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.modelo.tarefas;

/**
 * @author faapereira
 *
 */
public enum StatusLista {	
	ATIVA(0,"Lista Ativa"),	
	INATIVA(1,"Lista Inativa"),
	FINALIZADA(2,"Lista Finalizada");
	
	
	
	private Integer codigo;

	private String descricao;
	StatusLista(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;

	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	
}




