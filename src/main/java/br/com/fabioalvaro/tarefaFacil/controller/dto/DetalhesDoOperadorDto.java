package br.com.fabioalvaro.tarefaFacil.controller.dto;

import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Loja;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Operador;

public class DetalhesDoOperadorDto {
	//campos
			private Long id;
			private String nome;
			private Loja loja;
			
			/*
			 *  construtor que recebe uma Rede e se preeche com os dados dela
			 */
			public  DetalhesDoOperadorDto(Operador operador) {
				this.id = operador.getId();
				this.nome = operador.getNome();
				this.loja = operador.getLoja();
			}
			//getters and setters

			public Long getId() {
				return id;
			}

			public void setId(Long id) {
				this.id = id;
			}

			public String getNome() {
				return nome;
			}

			public void setNome(String nome) {
				this.nome = nome;
			}

			public Loja getLoja() {
				return loja;
			}

			public void setLoja(Loja loja) {
				this.loja = loja;
			}

		


		
			
			
}
