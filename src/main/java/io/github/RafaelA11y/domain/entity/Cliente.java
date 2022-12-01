package io.github.RafaelA11y.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    /*@NotNull: Não permite um valor nulo, porém permite um valor vazio (no caso de String).
      @NotEmpty: Assim como a @NotNull, não permite valor nulo e além disso seu tamanho deve ser maior que zero. Espaços em brancos são
        levados em conta na verificação de tamanho do valor. Ou seja, o json recebido pela web service terá que ter um campo nome e este deverá
        estar preenchido, não sendo aceito campo nome nulo ouy de String vazia.
      @NotBlank: Assim como a @NotEmpty, não permite valor nulo e o comprimento (sem considerar espaços em branco) deve ser maior que zero.*/
    @Column(name = "nome", length = 100)
    @NotEmpty(message = "Campo nome é obrigatório")
    private String nome;

    /*A anotação @CPF inclui a lógica de cpf de 11 dígitos que sejam válidos, enquanto que @NotEmpity não permite campo em branco.*/
    @Column(name = "cpf", length = 11)
    @NotEmpty(message =  "Campo CPF é obrigatório")
    @CPF(message = "Informe um CPF válido")
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
