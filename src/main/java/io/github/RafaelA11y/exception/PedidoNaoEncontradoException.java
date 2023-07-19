package io.github.RafaelA11y.exception;

public class PedidoNaoEncontradoException extends RuntimeException
{
    public PedidoNaoEncontradoException() {
        super("Pedido não encontrado.");
    }
}
