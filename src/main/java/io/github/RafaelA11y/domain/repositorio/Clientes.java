package io.github.RafaelA11y.domain.repositorio;

import io.github.RafaelA11y.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
    private static String INSERT = "INSERT INTO CLIENTE(NOME) VALUES(?);";
    private static String SELECT_ALL = "SELECT* FROM CLIENTE;";
    /*O objeto JdbcTemplate possui todos os métodos padrão SQL, este objeto também já possui uma conexão JDBC embutida e devidamente configurada, sendo
    * assim, basta instanciar o objeto e começar a usar seus métodos para fazer operações na base de dados.*/
    @Autowired
    JdbcTemplate jdbcTemplate;
    /*O método JdbcTemplate update(String sql, Object[]) suporta operações de inserção, alteração e deleção, seu primeiro parâmetro é a String SQL e o
    * segundo parâmetro é um array de Object contendo os parâmetros do objeto.*/
    public Cliente salvarCliente(Cliente cliente)
    {
        jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
        return cliente;
    }
    /*O método obterTodos() recupera todos os registros da tabela CLIENTE que está dentro do banco de dados. O método query(String query, RowMapper rm<E>)
    * recebe a String SQL de consulta enquanto que o objeto RowMapper<Cliente> recupera o ResultSet rs e mapeia os registros dentro dele para a classe entre
    * generics, no caso, Cliente. A classe RowMapper é declarada de forma anônima e possui o método mapRow(ResultSet rs, int rowNum) que deve ser sobrescrito
    * retornando o objeto Cliente, o método getString(String column) serve para preencher o objeto Cliente que será retornado faz isso para gente, seu
    * parâmetro String é o nome da coluna que deverá ser usada para preencher os atributos dos vários objetos que serão retornados para nós. O método
    * getInt(String columnLabel) serve para retornar um valor inteiro da coluna passada de parâmetro em formato String, isso porquê o objeto Cliente
    * precisa ser preenchido com um valor inteiro (pois seu construtor espera receber um String nome e um Integer id) para o seu atributo id.
    * Lembre-se, os registros da base de dados já tem seu id criado de forma automática pela cláusula AUTO_INCREMENT, mas para os objetos Cliente's  que
    * estarão na List<Cliente> serem construídos, é preciso passar um valor inteiro para sua construção */
    public List<Cliente> obterTodos()
    {
        return jdbcTemplate.query(SELECT_ALL, new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Cliente(rs.getString("NOME"), rs.getInt("ID"));
            }
        });
    }
}
