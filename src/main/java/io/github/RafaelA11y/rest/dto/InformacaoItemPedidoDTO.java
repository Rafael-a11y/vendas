package io.github.RafaelA11y.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/*Classe que representa a visualização dos itens do pedido para o front quando este faz uma requisição http get. Uma classe DTO é útil para situações
* que não é necessário expor para o frontend todos os dados da entidade requisitada, exemplo disso é o id do produto no banco de dados que não será
* reveladop para o front quiando este objeto for retornado. A anotação @Data aglomera @Getter, @Setter, @RequiredArgsConstructor, @ToString,
* @EqualsAndHashCode, e lombok.Value*/
public class InformacaoItemPedidoDTO
{
    private String descricaoProduto;
    private BigDecimal precoUnitario;
    private Integer quantidade;
}
