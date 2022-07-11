/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.modelo.tarefas;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author faapereira
 *
 */
@Entity
public class Acao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private String gatilho;
	private String gatilhoRegra;
	private String gatilhoValor;
	@ManyToOne
	private Lista listaVinculada;
	@ManyToOne
	private Pergunta pergunta;
	private String prefixoPerguntaDescricao="";
	@Enumerated(EnumType.STRING)
	private AcaoTipo acaoTipo;

	
	
	
	public Acao() {
		
	}
	
	




	public Acao(String descricao, String gatilho, String gatilhoRegra, String gatilhoValor, Lista listaVinculada,
			Pergunta pergunta, AcaoTipo acaoTipo,String prefixoPerguntaDescricao) {

		this.descricao = descricao;
		this.gatilho = gatilho;
		this.gatilhoRegra = gatilhoRegra;
		this.gatilhoValor = gatilhoValor;
		this.listaVinculada = listaVinculada;
		this.pergunta = pergunta;
		this.acaoTipo = acaoTipo;
		this.prefixoPerguntaDescricao=prefixoPerguntaDescricao;
	}






	public String getPrefixoPerguntaDescricao() {
		return prefixoPerguntaDescricao;
	}






	public void setPrefixoPerguntaDescricao(String prefixoPerguntaDescricao) {
		this.prefixoPerguntaDescricao = prefixoPerguntaDescricao;
	}






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






	public String getGatilho() {
		return gatilho;
	}






	public void setGatilho(String gatilho) {
		this.gatilho = gatilho;
	}






	public String getGatilhoRegra() {
		return gatilhoRegra;
	}






	public void setGatilhoRegra(String gatilhoRegra) {
		this.gatilhoRegra = gatilhoRegra;
	}






	public String getGatilhoValor() {
		return gatilhoValor;
	}






	public void setGatilhoValor(String gatilhoValor) {
		this.gatilhoValor = gatilhoValor;
	}






	public Lista getListaVinculada() {
		return listaVinculada;
	}






	public void setListaVinculada(Lista listaVinculada) {
		this.listaVinculada = listaVinculada;
	}






	public Pergunta getPergunta() {
		return pergunta;
	}






	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}






	public AcaoTipo getAcaoTipo() {
		return acaoTipo;
	}






	public void setAcaoTipo(AcaoTipo acaoTipo) {
		this.acaoTipo = acaoTipo;
	}






	@Override
	public String toString() {
		return "Acao [id=" + id + ", descricao=" + descricao + ", gatilho=" + gatilho + ", gatilhoRegra=" + gatilhoRegra
				+ ", gatilhoValor=" + gatilhoValor + ", listaVinculada=" + listaVinculada.getId() + ", pergunta=" + pergunta.getId()
				+ ", acaoTipo=" + acaoTipo + "]";
	}





	
	
	

}
