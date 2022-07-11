package br.com.fabioalvaro.tarefaFacil.controller.dto;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Loja;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Rede;

public class DetalhesDaLojaDto {
	//campos
		private Long id;
		private String descricao;
		private Rede rede;
		
		/*
		 *  construtor que recebe uma Rede e se preeche com os dados dela
		 */
		public  DetalhesDaLojaDto(Loja loja) {
			this.id = loja.getId();
			this.descricao = loja.getDescricao();
			this.rede = loja.getRede();
		}
		
		
		//getters and setters

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

		public Rede getRede() {
			return rede;
		}

		public void setRede(Rede rede) {
			this.rede = rede;
		}



	
		
		
}
