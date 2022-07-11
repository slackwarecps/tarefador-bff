/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author faapereira
 *
 */
@Controller
public class HealthCheckController {
	
	//@Value("${HOME}") 
	//private String home="VAZIO";
	@Value("${spring.datasource.username:valor_default}")
	private String username;
	
	@RequestMapping("/health-check")
	@ResponseBody
	public String healthCheck() {
		String body;
		body = "<h1>health-check!!</h1> "
				
				+ "<br>username: "+ username + ""
				+ "<br>pau.no.gato: "+ username + ""
				+ "<br>pau.no.gato: "+ username + ""
				;
		return body;
	}

}
