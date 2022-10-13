package io.github.RafaelA11y.domain.entity;

import javax.persistence.*;
import java.util.Set;
// name = o nome que a tabela a ser criada terá.
@Entity
@Table(name = "cliente")
public class Cliente
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome", length = 100)
    private String nome;

    public Cliente() {}

    public Cliente(String nome)
    {
        this.nome = nome;
    }

    /*Usamos o Atributo mappedBy porque a tabela pedido possui uma foreign cliente_id que aponta para a tabela cliente, para tornar os
     dados da tabela pedido visível para a classe Cliente, usa-se o @OneToMany(mappedBy = 'cliente') para que seja possível a partir de
     uma instância da classe Cliente, puxar os registros da tabela pedido, dando uma visão bidirecional dos dados. A prppriedade FetchType.LAZY serve
     para quando um cliente for carregado, os pedidos associados a ele não serem carregados juntos, pois pode tornar a pesquisa desnecessariamente
     custosa em termo de processamento.*/
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private Set<Pedido> pedidos;

    public Cliente(String nome, Integer id)
    {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public Set<Pedido> getPedidos() {return pedidos;}

    public void setPedidos(Set<Pedido> pedidos) {this.pedidos = pedidos;}

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
