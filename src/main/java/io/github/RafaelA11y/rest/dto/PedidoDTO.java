package io.github.RafaelA11y.rest.dto;

import io.github.RafaelA11y.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

//      {
//        "cliente" : 1,
//        "total" : 100,
//        "itens" : [
//                      {
//                          "produto" : 1,
//                          "quantidade" : 10
//                       }
//                  ]
//      }
/*Esta classe DTO reprersenta a realização de um pedido para uma requisição http POST. Classes DTO's são bastante úteis para resumir de forma adequada
* os procvessos da aplicação, pegando por exemplo a inserção de um novo pedido na base de dados, caso seja usado a classe Pedido para a inserção de um
* novo pedido, o usuário final do frontend teria que informar todos os dados do cliente associado, pois dentro da entidade Pedido, temos um private
* Cliente, e tudo o que precisamos é o id do cliente e não um objeto completo de Cliente. Também não é preciso definir um status para o novo pedido
* pois tal atributo é gardo após a realização do pedido pela web service em io.github.RafaelA11y.service.impl.PedidoServiceImpl, e nem uma data para
* a classe DTO pois a data de realização também é gerada pela web service pela io.github.RafaelA11y.service.impl.PedidoServiceImpl Por isso a
* importância de uma classe DTO, pois dependendo da operação, não é necessário os vários atributos do objeto a ser inserido no banco de dados.*/
public class PedidoDTO
{

    @NotNull(message = "Informe o código do cliente")
    private Integer cliente; //Representa o id do cliente
    @NotNull(message = "Campo total do pedido é obrigatório")
    @Min(value = 0)
    private BigDecimal total;//Representa o valor total do pedido
    @NotEmptyList(message = "Pedido não pode ser realizado sem itens")
    private List<ItemPedidoDTO> itens;
    /*Repare que o array do json é abstraído para uma List<ItemPedidoDTO> itens, ainda assim é necessário uma classe
    ItemPedidoDTO para representar individualmente cada elemento do array do json, pois não queremos inserir todos os dados*/
}
