package io.github.RafaelA11y.domain.repository;

import io.github.RafaelA11y.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer>
{

}
