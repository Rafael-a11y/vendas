package io.github.RafaelA11y.domain.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity @Table(name = "produto")
public class Produto
{
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id")
    private Integer id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getDescricao()
    {
        return descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoUnitario()
    {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario)
    {
        this.precoUnitario = precoUnitario;
    }
}
