package io.github.RafaelA11y.domain.repository;

import io.github.RafaelA11y.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedido extends JpaRepository<ItemPedido, Integer>
{

}
