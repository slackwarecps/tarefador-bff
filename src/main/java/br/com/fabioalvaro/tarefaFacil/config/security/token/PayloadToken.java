/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.config.security.token;

import com.google.gson.Gson;

import br.com.fabioalvaro.tarefaFacil.modelo.forum.Usuario;

/**
 * @author faapereira
 *
 */
public class PayloadToken {
	private Long idade;
	private String sexo;
	private Long idUsuario;
	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public PayloadToken() {
		
	}
	
	
	public PayloadToken(Long idade, String sexo, Long idUsuario,Usuario usuario) {
		
		this.idade = idade;
		this.sexo = sexo;
		this.idUsuario = idUsuario;
		this.usuario = usuario;
		
	}
	
	
	public Long getIdade() {
		return idade;
	}
	public void setIdade(Long idade) {
		this.idade = idade;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
	/**
	 * @return json 
	 */
	public String toJson(PayloadToken payloadToken) {
		System.out.println(new Gson().toJson(payloadToken));
		return new Gson().toJson(payloadToken);
	}
	/**
	 * @param subject
	 * @return
	 */
	public PayloadToken converter(String subjectJson) {
		return new Gson().fromJson(subjectJson, PayloadToken.class);
	}


	@Override
	public String toString() {
		return "PayloadToken [idade=" + idade + ", sexo=" + sexo + ", idUsuario=" + idUsuario + "]";
	}
	


}
