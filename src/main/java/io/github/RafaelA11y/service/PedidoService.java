package io.github.RafaelA11y.service;

import io.github.RafaelA11y.domain.entity.Pedido;
import io.github.RafaelA11y.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService
{
    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);
}
