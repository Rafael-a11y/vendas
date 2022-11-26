package io.github.RafaelA11y.rest.controller;

import io.github.RafaelA11y.domain.entity.Pedido;
import io.github.RafaelA11y.rest.dto.PedidoDTO;
import io.github.RafaelA11y.service.PedidoService;
import  static org.springframework.http.HttpStatus.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"api/pedidos"})
public class PedidoController
{
    // PedidoService é uma interface, quem de fato vai ser responsável por gerenciar as regras de negócio é sua classe implementadora.
    private PedidoService service;

    public PedidoController(PedidoService service)
    {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody PedidoDTO dto)
    {
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }
}
