package io.github.RafaelA11y.domain.repositorio;

import io.github.RafaelA11y.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*A interface JpaRepository serve para substituir uma classe com @Repository, pois assim o programador
* não irá precisar implementar na mão os métodos de persistência, pois a interface já possui esses
* métodos, note que não é preciso declarar anotações nesta interface.
* O Spring irá criar um objeto anônimo que irá escrever o corpo dos métodos herdados deJpaRepository.
*  */
public interface Clientes extends JpaRepository<Cliente, Integer>
{

    List<Cliente> findByNomeLike(String nome);
}