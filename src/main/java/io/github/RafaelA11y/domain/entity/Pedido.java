package io.github.RafaelA11y.domain.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity @Table(name = "pedido")
public class Pedido
{
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id")
    private Integer id;

    @ManyToOne @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "data_pedido")
    private LocalDate dataPedido;


    /*Como o tipo equivalente no banco é numeric(20,2), é necessário especificar isso na classe equivalente, usando o atributo
    * length = 20 e o precision = 2*/
    @Column(name = "preco_total", length = 20, precision = 2)
    private BigDecimal precoTotal;

    /*Usamos o Atributo mappedBy porque a tabela item_pedido possui uma foreign pedido_id que aponta para a tabela pedido, para tornar os
     dados da tabela pedido visível para a tabela item_pedido, usa-se o @OneToMany(mappedBy = 'cliente') para que seja possível a partir de
     uma instância da classe Pedido, puxar os registros da tabela item_pedido, dando uma visão bidirecional dos dados*/
    @OneToMany(mappedBy = "pedido")
    private Set<ItemPedido> itens;

    public Integer getId() {return this.id;}

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Cliente getCliente()
    {
        return cliente;
    }

    public void setCliente(Cliente cliente)
    {
        this.cliente = cliente;
    }

    public LocalDate getDataPedido()
    {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido)
    {
        this.dataPedido = dataPedido;
    }

    public BigDecimal getPrecoTotal()
    {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal)
    {
        this.precoTotal = precoTotal;
    }

    public Set<ItemPedido> getItens() {return itens;}

    public void setItens(Set<ItemPedido> itens) {this.itens = itens;}

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", dataPedido=" + dataPedido +
                ", precoTotal=" + precoTotal +
                '}';
    }
}
