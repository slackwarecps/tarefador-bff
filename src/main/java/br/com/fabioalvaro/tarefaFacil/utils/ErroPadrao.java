/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import com.google.gson.Gson;

import br.com.fabioalvaro.tarefaFacil.config.ErroDeFormularioDto;

/**
 * @author faapereira
 *
 */
@Component
public class ErroPadrao {
	private Boolean hasError = false;
	private List<ErroDeFormularioDto> listaDeErros = new ArrayList<>();
	/** The error. */
	private List<ErroDeFormularioDto> error = new ArrayList<>();

	ErroPadrao() {
	
	}

	public ResponseEntity<?> badRequest(String campo, String erro) {
		
		ErroDeFormularioDto erroFormato = new ErroDeFormularioDto(campo, erro);
		// List<ErroDeFormularioDto> listaDeErros = new ArrayList<>();
		listaDeErros.add(erroFormato);

		return new ResponseEntity<>(

				new Gson().toJson(listaDeErros), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<?> response() {
		
		return new ResponseEntity<>(new Gson().toJson(error), HttpStatus.BAD_REQUEST);
		
	}

	public void addError(String campo, String erro) {
		ErroDeFormularioDto erroFormato = new ErroDeFormularioDto(campo, erro);
		listaDeErros.add(erroFormato);
		this.error.add(erroFormato);
		setHasError(true);
	}

	public Boolean hasError() {
		return !error.isEmpty();
	}

	public void setHasError(Boolean hasError) {
		this.hasError = hasError;
	}

	/**
	 * @param erroDeFormularioDto
	 */
	public void addError(ErroDeFormularioDto erroDeFormularioDto) {
		this.error.add(erroDeFormularioDto);

	}

	/**
	 * 
	 */
	public void start() {
		error.clear();
		
	}

}
