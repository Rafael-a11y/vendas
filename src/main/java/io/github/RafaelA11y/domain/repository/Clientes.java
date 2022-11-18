package io.github.RafaelA11y.domain.repository;

import io.github.RafaelA11y.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*A interface JpaRepository serve para substituir uma classe com @Repository, pois assim o programador não irá precisar
 implementar na mão os métodos de persistência, pois a interface já possui esses métodos, note que não é preciso
 declarar anotações nesta interface. O Spring irá criar um objeto anônimo do tipo SimpleJpaRepository que irá ser o
 resposável pela implementação dos métodos herdados de JpaRepository. Os query methods são métodos que geram consultas
 jpql em tempo de execução, eles seguem uma convenção que pode ser encontrada na documentação do Spring */
public interface Clientes extends JpaRepository<Cliente, Integer>
{
    List<Cliente> findByNomeLike(String nome);

    boolean existsByNome(String nome);

    /*Você também criar querys customizadas com a anotação @Query, o atributo value de @Query serve para guardar
    * a consultaem formato String, o :nome é o parâmetro que será passado para a consulta em jpql que será gerada
    * em tempo de execução. A anotação @Param("nome") serve para linkar o parâmetro do método com o parâmetro da
    * query. É importante ressaltar que a língua jpql não olha para a tabela mas para a classe. Isso significa que
    * por exemplo, se o nome da tabela fosse clientezinho, a consulta ainda assim funcionaria pois o método olha
    * para a classe Cliente que está mapeada com a tabela clientezinho.*/
    @Query(value = "select c from Cliente c where c.nome like :nome")
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    /*Você também pode usar o atributo nativeQuery = true para pode usar consultas nativas em SQL
    * caso não goste de usar jpql,claro que a String deve conter um valor de consulta SQL, óbvio.
    * */
    @Query(value = "select * from cliente c where c.nome like '%:nome%' ", nativeQuery = true)
    List<Cliente> encontrarPorNomeComSqlNativo(@Param("nome") String nome);

    /*Caso você queira usar query methods para fazer algum tipo de alteração na tabela como deletar
    * um registro, use a anotação @Transactional*/
    @Transactional
    void deleteByNome(String nome);

    /*No caso de query customizadas que também fazem algum tipo de alteração na tabela como a
    * exclusão de um registro, use além do @Transactional, o @Modifying, que tem o mesmo objetiivo
    * que @Transactional, mas é usado em query customizadas que fazem alterações na tabela como a
    * exclusão de um registro.*/
    @Transactional
    @Modifying
    @Query(value = "delete from Cliente c where c.nome=:nome")
    void deleteByNomeComQuery(@Param("nome") String nome);

    /*Na consulta, a cláusula left join c.pedidos serve para carregar todos os clientes que tem ou não pedidos, sem o
    * left, o Spring carrega apenas os clientes que possuem pedidos, no final a consulta retorna o cliente junto de
    * seus pedidos*/
    @Query("select c from Cliente c left join fetch c.pedidos where c.id = :id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);

    Optional<Cliente> findById(Integer id);
}