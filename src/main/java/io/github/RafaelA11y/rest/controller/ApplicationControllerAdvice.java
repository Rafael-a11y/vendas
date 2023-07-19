package io.github.RafaelA11y.rest.controller;

import io.github.RafaelA11y.exception.PedidoNaoEncontradoException;
import io.github.RafaelA11y.exception.RegraNegocioException;
import io.github.RafaelA11y.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/*Classe que serve exclusivamente para tratar exceções, o uso da palavra Rest serve para substituir uso de @ResponseBody nos métodos que respondem
requisição http. A anotação @RestControllerAdvice transforma a classe numa tratadora de exceções, e é claro, por estar dentro do contexto do Spring, não
precisa ser instanciada.
*/
@RestControllerAdvice
public class ApplicationControllerAdvice
{
    /*Tratador de RegraNegocioException, sempre que um RegraNegocioException for gerado este método será acionado para tratar a exceção gerada
    Vale lembrar que este método também retorna uma http status 400 bad request por conta do @ResponseStatus⇾ Substitui uso de try/catch*/
    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex)
    {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException ex)
    {
        return new ApiErrors(ex.getMessage());
    }

    /*Retorna a message de @NotEmpity dos campos marcados como @NotEmpity, pode ser que várias messages sejam retornada na lista pois MathodArgument
    * NotValidException é ligado para todos os campos de todas as classes com validções em seus campos, sendo assim se vários campos campos estiverem
    * marcados com @NotEmpty, todas as messages destes campos deixados em campos serão devolvidos na lista que este método retorna para o construtor
    * de ApiErroros que recebe uma lista com as mensagens de erro.*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors HandleMethodNotValidException(MethodArgumentNotValidException ex)
    {
        List<String> erros = ex.getBindingResult().getAllErrors()    .stream().map(erro -> erro.getDefaultMessage()).collect(Collectors.toList());
        return new ApiErrors(erros);
    }
}
