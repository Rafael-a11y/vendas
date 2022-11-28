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
//Classe que representa a visualização dos itens do pedido para o front quando este faz uma requisição http get
public class InformacaoItemPedidoDTO
{
    private String descricaoProduto;
    private BigDecimal precoUnitario;
    private Integer quantidade;
}
