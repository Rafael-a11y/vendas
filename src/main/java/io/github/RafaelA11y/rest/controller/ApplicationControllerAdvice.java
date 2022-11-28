package io.github.RafaelA11y.rest.controller;

import io.github.RafaelA11y.exception.RegraNegocioException;
import io.github.RafaelA11y.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//Classe que serve exclusivamente para tratar exceções, o uso da palavra Rest serve para substituir uso de @ResponseBody nos métodos que respondem
//requisição http
@RestControllerAdvice //Transforma a classe numa tratadora de exceções
public class ApplicationControllerAdvice
{
    /*Tratador de RegraNegocioException, sempre que um RegraNegocioException for gerado este método será acionado para tratar a exceção gerada
    Vale lembrar que este método também retorna uma http status 400 bad request por conta do @ResponseStatus⇾ Substitui uso de try/catch*/
    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex)
    {
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }
}
