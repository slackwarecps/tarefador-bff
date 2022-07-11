package br.com.fabioalvaro.tarefaFacil.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	
	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) {
		List<ErroDeFormularioDto> listaErrosdto = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(erroItem -> {
			String mensagem = messageSource.getMessage(erroItem, LocaleContextHolder.getLocale());
			ErroDeFormularioDto erroParseado = new ErroDeFormularioDto(erroItem.getField(),mensagem);
			listaErrosdto.add(erroParseado);
		});
		
		return listaErrosdto;
	}
	
}
