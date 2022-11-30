package io.github.RafaelA11y.domain.entity;

import io.github.RafaelA11y.domain.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "pedido")
public class Pedido
{
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id") //@JoinColumn serve para colunas que representam Foreign Keys no banco de dados
    private Cliente cliente;

    @Column(name = "data_pedido")
    private LocalDate dataPedido;


    /*Como o tipo equivalente no banco é numeric(20,2), é necessário especificar isso na classe equivalente, usando o atributo
    * precision = 20 e o scale = 2*/
    @Column(name = "preco_total", precision = 20, scale = 2)
    private BigDecimal precoTotal;

    /*@Enumerated(EnumType.STRING) serve para o JPA transformar o campo enum da entidade em um campo string no banco de dados.*/
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedido status;

    /*Usamos o Atributo mappedBy porque a tabela item_pedido possui uma foreign pedido_id que aponta para a tabela pedido, para tornar os
     dados da tabela pedido visível para a tabela item_pedido, usa-se o @OneToMany(mappedBy = 'cliente') para que seja possível a partir de
     uma instância da classe Pedido, puxar os registros da tabela item_pedido, dando uma visão bidirecional dos dados*/
    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;

    public Pedido adicionarCliente(Cliente cliente)
    {
        this.setCliente(cliente);
        return this;
    }

    public Pedido adicionarDataPedido(LocalDate data)
    {
        this.setDataPedido(data);
        return this;
    }

    public Pedido adicionarPreco(BigDecimal valor)
    {
        this.setPrecoTotal(valor);
        return this;
    }

    public List<ItemPedido> getItens()
    {
        if(this.itens == null) this.itens = new ArrayList<>();
        return this.itens;
    }
}
