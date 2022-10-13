package io.github.RafaelA11y.domain.repository;

import io.github.RafaelA11y.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pedidos extends JpaRepository<Pedido, Integer>
{

}
