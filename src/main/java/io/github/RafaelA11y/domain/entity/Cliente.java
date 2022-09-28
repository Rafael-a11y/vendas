package io.github.RafaelA11y.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente
{
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

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
