package io.github.RafaelA11y.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/*Classe que serve para abstrair a resposta de uma requisição http get, exemplo: quando o consulta sua lista de pedidos feitos. Os atributos desta
  classe DTO serão retornados para o frontend. é importante ressaltar mais uma vez a importância de uma classe DTO, pois dependendo do contexto,
  existem alguns dados que não dvem ser revelados para o front, como o id do cliente no banco de dados, sendo assim é uma boa prática fazer uso de DTO's
  para adptar melhor as respostas às equisições http. A anotação @Builder serve para que a gente consiga criar a instância sem chamar o construtor
  utilizando o design pattern Builder*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacoesPedidoDTO
{
    private Integer codigo;
    private String cpf;
    private String nomeCliente;
    private BigDecimal total;
    private String dataPedido;
    private String status;
    private List<InformacaoItemPedidoDTO> itens;

}
