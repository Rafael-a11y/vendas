package io.github.RafaelA11y.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*Serve para a abstração de uma requisição http post, de um item de pedido, pois se for usado a entidade ItemPedido será necessário inserir dados
* desnecessários como um objeto cliente, um objeto produto etecetera, para a operação de inserção, é necessário apenas o id do produto e a quantidade.*/
public class ItemPedidoDTO
{
    private Integer produto; //Id do produto
    private Integer quantidade;//Quantidade de itens.

}
