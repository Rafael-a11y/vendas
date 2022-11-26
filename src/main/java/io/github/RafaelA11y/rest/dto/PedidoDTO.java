package io.github.RafaelA11y.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class PedidoDTO
{
    private Integer cliente;
    private BigDecimal total;
    private List<ItemPedidoDTO> itens; //Repare que o array do json é abstraído para uma List<ItemPedidoDTO> itens, ainda assim é necessário uma classe
    // ItemPedidoDTO para representar individualmente cada elemento do array do json.
}
