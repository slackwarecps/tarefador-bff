package br.com.fabioalvaro.tarefaFacil.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Rede;

/**
 * Classe que recebe o form de cadastro
 *
 * @author faapereira
 *
 */
public class RedeForm {

	@NotNull
	@NotEmpty
	@Length(min = 3)
	private String descricao;

	private Boolean ativa;

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Boolean getAtiva() {
		return ativa;
	}
	public void setAtiva(Boolean ativa) {
		this.ativa = ativa;
	}
	
	
	public Rede converter() {
		return new Rede (descricao,ativa);
	}
	
	
	@Override
	public String toString() {
		return "RedeForm [descricao=" + descricao + ", ativa=" + ativa + ", getDescricao()=" + getDescricao()
				+ ", getAtiva()=" + getAtiva() + ", converter()=" + converter() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	
	

}
