package br.com.fabioalvaro.tarefaFacil.modelo.tarefas;

public enum PerguntaStatus {
NAO_RESPONDIDA(0,"Nao respondida"),
RESPONDIDA(1,"Respondida"),
IGNORADA(2,"Ignorada");


private Integer codigo;

private String descricao;
PerguntaStatus(Integer codigo, String descricao) {
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
