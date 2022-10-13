package io.github.RafaelA11y.domain.entity;

import javax.persistence.*;

@Entity @Table(name = "item_pedido")
public class ItemPedido
{
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id")
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

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Pedido getPedido()
    {
        return pedido;
    }

    public void setPedido(Pedido pedido)
    {
        this.pedido = pedido;
    }

    public Produto getProduto()
    {
        return produto;
    }

    public void setProduto(Produto produto)
    {
        this.produto = produto;
    }

    public Integer getQuantidade()
    {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade)
    {
        this.quantidade = quantidade;
    }
}
