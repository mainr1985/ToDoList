package br.com.profdanieleleao.todolist.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//classe criada para customizar mensagens de erros para exceções
@ControllerAdvice //anotação que serve pra definir classes globais no tratamento de exceções
public class ExceptionHandlerController {
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMostSpecificCause().getMessage()); //getMessage vem do método criado em TaskModel
	}
}
