package io.github.RafaelA11y.rest;

import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
public class ApiErrors
{
    @Getter
    private List<String> errors;

    public ApiErrors(String mensagemErro)
    {
        this.errors = Arrays.asList(mensagemErro); //Retorna uma List de tamanho fixo com a mensagem de erro
    }
}
