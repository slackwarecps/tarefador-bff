package br.com.fabioalvaro.tarefaFacil.modelo.tarefas;

public enum PerguntaTipo {	
	ESCOLHA_SIMPLES(0),
	ESCOLHA_MULTIPLA(1),
	NUMERO_INTEIRO(2),
	NUMERO_DECIMAL(3),
	BOOLEAN(4),
	FOTO_UNICA(5),
	FOTO_MULTIPLAS(6),
	TEXTO_LIVRE(7),
	ARQUIVO_UNICO(8),
	ARQUIVO_MULTIPLAS(9),
	TREINAMENTO(10);
	
	private int val;

	PerguntaTipo(int value) {
		val = value;
	}	
	
	public int getValue(){
        return val;
    }
	
}
