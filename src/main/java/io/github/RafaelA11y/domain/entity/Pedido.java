package io.github.RafaelA11y.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pedido
{
    private Integer id;
    private Cliente cliente;
    private LocalDate data_Pedido;
    private BigDecimal precoTotal;

    public Integer getId()
    {
        return this.id;
    }


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

    public LocalDate getData_Pedido()
    {
        return data_Pedido;
    }

    public void setData_Pedido(LocalDate data_Pedido)
    {
        this.data_Pedido = data_Pedido;
    }

    public BigDecimal getPrecoTotal()
    {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal)
    {
        this.precoTotal = precoTotal;
    }
}
