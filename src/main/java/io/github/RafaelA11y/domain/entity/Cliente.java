package io.github.RafaelA11y.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(name = "cpf", length = 11)
    private String cpf;

    public Cliente(String nome)
    {
        this.nome = nome;
    }

    /*Usamos o Atributo mappedBy porque a tabela pedido possui uma foreign cliente_id que aponta para a tabela cliente, para tornar os
     dados da tabela pedido visível para a classe Cliente, usa-se o @OneToMany(mappedBy = 'cliente') para que seja possível a partir de
     uma instância da classe Cliente, puxar os registros da tabela pedido, dando uma visão bidirecional dos dados. A prppriedade FetchType.LAZY serve
     para quando um cliente for carregado, os pedidos associados a ele não serem carregados juntos, pois pode tornar a pesquisa desnecessariamente
     custosa em termo de processamento. O @JsonIgnore serve para que a propriedade em questão seja inorada e não seja passda para o Json quando devolcermos
     json nno corpo da resposta da requisição. Claro que só faz sentido para consultas de busca preguiçosa. Voltar aqui para testar se os pedidos são
     ignorados numa consulta em que eu quero os pedidos juntos hehe.*/
    @JsonIgnore
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Pedido> pedidos;

    public Cliente(String nome, Integer id)
    {
        this.id = id;
        this.nome = nome;
    }
}
