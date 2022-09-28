package io.github.RafaelA11y.domain.repositorio;

import io.github.RafaelA11y.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/*A anotação @Repository serve para dizer ao Spring que esta classe será responsável por acessar a base de dados, essa anotação também possibilita
* a tradução das exceções que acontecem no banco de dados, usar a anotação @Component também possibilitaria a reutilização dessa classe em outras
* classes para injeção de dependência, mas não ofereceria a funcionalidade de tradução das exceções do banco de dados*/
@Repository
public class Clientes
{
    /*A constante String INSERT representa a inserção em SQL na tabela CLIENTE dentro de nosso esquema*/
//  private static final String INSERT = "INSERT INTO CLIENTE(NOME) VALUES(?)";
    private static final String SELECT_ALL = "SELECT * FROM CLIENTE";
    private static final String UPDATE = "UPDATE CLIENTE SET NOME = ? WHERE ID = ?";
    private static final String DELETE = "DELETE FROM CLIENTE WHERE ID = ?";
    /*O objeto JdbcTemplate possui todos os métodos padrão SQL, este objeto também já possui uma conexão JDBC embutida e devidamente configurada, sendo
    * assim, basta instanciar o objeto e começar a usar seus métodos para fazer operações na base de dados.*/
    @Autowired
    JdbcTemplate jdbcTemplate;
    /*O método JdbcTemplate update(String sql, Object[]) suporta operações de inserção, alteração e deleção, seu primeiro parâmetro é a String SQL e o
    * segundo parâmetro é um array de Object contendo os parâmetros do objeto.*/

    /*A anotação @Trasactional de org.springframework.transaction.annotation.Transactional é usada para gente não precisar ficar chamando os métodos
    * entityManager.getTransaction().begin e entityManager.getTransaction().begin antes e após o entityManager.persist(cliente) respectivamente*/
    @Autowired
    EntityManager entityManager;
    @Transactional
    public Cliente salvarCliente(Cliente cliente)
    {
        entityManager.persist(cliente);
//      jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
        return cliente;
    }
    /*O método obterTodos() recupera todos os registros da tabela CLIENTE que está dentro do banco de dados. O método query(String query, RowMapper rm<E>)
    * recebe a String SQL de consulta enquanto que o objeto RowMapper<Cliente> recupera o ResultSet rs e mapeia os registros dentro dele para a classe entre
    * generics, no caso, Cliente. A classe RowMapper é declarada de forma anônima e possui o método mapRow(ResultSet rs, int rowNum) que deve ser sobrescrito
    * retornando o objeto Cliente, o método getString(String column) serve para preencher o objeto Cliente que será retornado para gente, seu
    * parâmetro String é o nome da coluna que deverá ser usada para preencher os atributos dos vários objetos que serão retornados para nós. O método
    * getInt(String columnLabel) serve para retornar um valor inteiro da coluna passada de parâmetro em formato String, isso porquê o objeto Cliente
    * precisa ser preenchido com um valor inteiro (pois seu construtor espera receber um String nome e um Integer id) para o seu atributo id.
    * Lembre-se, os registros da base de dados já tem seu id criado de forma automática pela cláusula AUTO_INCREMENT, mas para os objetos Cliente's  que
    * estarão na List<Cliente> serem construídos, é preciso passar um valor inteiro para sua construção, neste caso, os ids dos registros recuperados de
    * dentro do banco de dados.
    * O método query(String query, RowMapper<E>) irá retornar uma List<Cliente>*/
    public List<Cliente> obterTodos()
    {
        return jdbcTemplate.query(SELECT_ALL, obterClienteMapper());
    }
    /*Este método retorna um objeto do tipo RowMapper<Cliente>, este objeto RowMapper sobrescreve e chama o método sobrescrito mapRow(ResultSet rs, int
    rowNum) que retorna um objeto do tipo especificado dentro do generics de RowMapper, ou seja, se for um RowMapper<Cachorro> então o método mapRow
     irá retornar um objeto Cachorro. A instanciação do objeto Cliente é feita usando o ResulSet rs que chama os métodos getString(String columnLabel) e
     o método getInt(String columnLabel), o primeiro retorna um valor textual representando o nome que é tirado da coluna cujo nome é passado de parâmetro,
     e o segundo um valor inteiro representando o id que é tirado da coluna cujo nome é passado por parâmetro. No fim das contas, o método obterClienteMapper()
     irá retornanr um RowMapper<Cliente> que serve para mapear a tabela CLIENTE com a classe Cliente. Outro ponto importante é que rowMap() lança
     SQLException*/
    private RowMapper<Cliente> obterClienteMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Cliente(rs.getString("NOME"), rs.getInt("ID"));
            }
        };
    }
    /* atualiza um registro da base de dados a partir da String query passada de parâmetro, no caso a String UPDATE, o array de Object serve para passar
    * os atributos do objeto Cliente que servirão de parâmetro para a consulta SQL*/
    public void atualizar(Cliente cliente)
    {
        jdbcTemplate.update(UPDATE, cliente.getNome(), cliente.getId());
        return;
    }

    public void deletar(Cliente cliente)
    {
        deletar(cliente.getId());
        return;
    }
    /**/
    public void deletar(Integer id)
    {
        jdbcTemplate.update(DELETE, id);
    }
    /* O método buscarPorNome irá retornar uma List<Cliente> com os registros cujo nome batem com a String passada de parâmetro, a chamada ao método
    * concat() serve para juntar a String SELECT_ALL com 'where nome like?', o array de Object serve apenas para fornecer o parâmetro para a consulta SQL
    * a ser feita, no caso '% + nome + %', o método obterRowmapperCliente() serve para fornecer o objeto RowMapper<Cliente> necessário para a execução ]
    * do método query(String query, Object... args)*/
    public List<Cliente> buscarPorNome(String nome)
    {
        return jdbcTemplate.query(
                SELECT_ALL.concat(" where nome like ? "), new Object[]{"%" + nome + "%"}, obterClienteMapper());
    }
}