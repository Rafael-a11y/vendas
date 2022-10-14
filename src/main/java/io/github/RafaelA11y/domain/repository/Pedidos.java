package io.github.RafaelA11y.domain.repository;

import io.github.RafaelA11y.domain.entity.Cliente;
import io.github.RafaelA11y.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pedidos extends JpaRepository<Pedido, Integer>
{
    /*Você pode sem nenhum problema recuperar pedidos a partir de um cliente, pois Pedidos está definido para recuperar
        Pedido certo? Mesmo que você faça a busca a partir de um objeto Cliente e com a busca definida como LAZY, os
        pedidos relacionados são retornados sem nenhum problema.
    No caso da query method abaixo, o cliente no nome se refere ao atributo cliente na classe Pedido*/
    List<Pedido> findByCliente(Cliente cliente);
}
