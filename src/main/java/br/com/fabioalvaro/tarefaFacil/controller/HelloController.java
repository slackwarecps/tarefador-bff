package br.com.fabioalvaro.tarefaFacil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	
	@RequestMapping("/")
	@ResponseBody
	public String index() {
		return "<h1>Tarefa Facil API</h1>"
				+ "<p>"
				+ "versao 03-Maio 12:13"
				+ "</p>" +""
						+ "<p>na resposta muda status da pergunta</p>" ;
	}

}
