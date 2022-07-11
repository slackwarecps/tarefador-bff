package br.com.fabioalvaro.tarefaFacil.controller.form;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Rede;
import br.com.fabioalvaro.tarefaFacil.repository.RedeRepository;

public class AtualizacaoRedeForm {
	private String descricao;
	private Boolean ativa;
	




	public Rede atualizar(Long id, RedeRepository redeRepository) {
		Rede rede = redeRepository.getOne(id);
		rede.setDescricao(this.descricao);
		rede.setAtiva(this.ativa);
		
		return rede;
	}





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
	
	
	
	

}
