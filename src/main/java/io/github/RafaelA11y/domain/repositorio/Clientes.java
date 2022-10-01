package io.github.RafaelA11y.domain.repositorio;

import io.github.RafaelA11y.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*A interface JpaRepository serve para substituir uma classe com @Repository, pois assim o programador
* não irá precisar implementar na mão os métodos de persistência, pois a interface já possui esses
* métodos, note que não é preciso declarar anotações nesta interface.
* O Spring irá criar um objeto anônimo que irá escrever o corpo dos métodos herdados deJpaRepository.
*
* Os query methods são métodos que geram consultas jpql em tempo de execução, eles seguem uma convenção
* que pode ser encontrada na documentação do Spring */
public interface Clientes extends JpaRepository<Cliente, Integer>
{
    List<Cliente> findByNomeLike(String nome);

    boolean existsByNome(String nome);

    /*Você também criar querys customizadas com a anotação @Query, o atributo value de @Query serve para guardar
    * a consultaem formato String, o :nome é o parâmetro que será passado para a consulta em jpql que será gerada
    * em tempo de execução. A anotação @Param("nome") serve para linkar o parâmetro do método com o parâmetro da
    * query*/
    @Query(value = "select c from Cliente c where c.nome like :nome")
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    @Query(value = "select * from cliente c where c.nome like '%:nome%' ", nativeQuery = true)
    List<Cliente> encontrarPorNomeComSqlNativo(@Param("nome") String nome);

    @Transactional
    void deleteByNome(String nome);

    @Transactional
    @Modifying
    @Query(value = "delete from Cliente c where c.nome=:nome")
    void deleteByNomeComQuery(@Param("nome") String nome);
}