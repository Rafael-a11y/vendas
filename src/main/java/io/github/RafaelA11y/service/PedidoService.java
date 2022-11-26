package io.github.RafaelA11y.service;

import io.github.RafaelA11y.domain.entity.Pedido;
import io.github.RafaelA11y.rest.dto.PedidoDTO;

public interface PedidoService
{
    Pedido salvar(PedidoDTO dto);
}
