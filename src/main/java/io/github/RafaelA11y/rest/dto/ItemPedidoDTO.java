package io.github.RafaelA11y.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ItemPedidoDTO
{
    private Integer produto; //Id do produto
    private Integer quantidade;//Quantidade de itens.

}
