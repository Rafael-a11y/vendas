package io.github.RafaelA11y.rest;

import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors
{
    @Getter
    private List<String> errors; //Lista que contêm a mensagem de erro específica da exception gerada.

    public ApiErrors(List<String> erros)
    {
        this.errors = erros;
    }

    public ApiErrors(String mensagemErro)
    {
        this.errors = Arrays.asList(mensagemErro);//Retorna uma ArrayList com a mensagem de erro já inserida.
    }
}
