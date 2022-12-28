package io.github.RafaelA11y.exception;

public class SenhaInvalidaException extends RuntimeException
{
    public SenhaInvalidaException()
    {
        super("Senha inválida, por favor insira uma senha válida.");
    }
}
