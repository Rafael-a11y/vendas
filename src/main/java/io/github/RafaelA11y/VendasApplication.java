package io.github.RafaelA11y;

import io.github.RafaelA11y.domain.entity.Cliente;
import io.github.RafaelA11y.domain.repositorio.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/*A anotação @SpringBootAplication é a anotação responsável para definir esta classe como a classe responsável por iniciar a aplicação Sping Boot*/
@SpringBootApplication
public class VendasApplication
{
    /*A anotação @Bean serve para aplicar a injeção de dependência, repare que o método CommandLineRunner iniciar() não é chamado dentro do main(), note
    * também que seu objeto não foi declarado em momento nenhum (pois, iniciar() não é um método estático), ou seja, nem seu objeto foi declrado como
    * também o método inicar não foi explícitamente chamado, o Spring criou um objeto anônimo que chamou o método iniciar().
    * O método iniciar irá salvar dois Cliente's na base dados e logo em seguida, irá recuperar estes dados através da chamada do método obterTodos()
    * que retorna uma lista preenchida com todos os registros da tabela CLIENTE inseridos dentro de objetos Cliente, logo em seguida, essa lista de
    * Cliente é impriomida no console a partir de um foreach(System.out::println)*/
    @Bean
    CommandLineRunner iniciar(@Autowired Clientes clientes)
    {
        return args -> {
            clientes.salvarCliente(new Cliente("Rafael Souto"));
            clientes.salvarCliente(new Cliente("Israel Souto"));

            List<Cliente> listaDeClientes = clientes.obterTodos();
            listaDeClientes.forEach(System.out::println);
        };
    }

    public static void main(String[] args)
    {
        /*Método responsável por niciar a aplicação Spring Boot, recebe como parâmetro, a classe responsável pela inicialização da aplicação (a classe
        que possui a anotação @SpringBootAplication), e o array String fornecido pelo main().*/
        SpringApplication.run(VendasApplication.class, args);
    }
}
