package io.github.RafaelA11y.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity @Table(name = "item_pedido")
public class ItemPedido
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Integer id;

    /*Como a tabela de item_pedido tem uma foreign key, não precisamos usar o mappedBy dentro de @ManyToOne. O @JoinColumn é similar
     * ao @Column, é usado em atributos mapeados para colunas foreign*/
    @ManyToOne @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    /*Como a tabela de item_pedido tem uma foreign key, não precisamos usar o mappedBy dentro de @ManyToOne. O @JoinColumn é similar
    * ao @Column, é usado em atributos mapeados para colunas foreign.*/
    @ManyToOne @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(name = "quantidade")
    private Integer quantidade;
}
