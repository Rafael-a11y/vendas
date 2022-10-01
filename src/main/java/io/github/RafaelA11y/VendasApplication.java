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
    * também o método inicar não foi explícitamente chamado, o Spring criou um objeto anônimo que chamou o método iniciar() por conta da anotação @Bean.
    * */
    @Bean
    CommandLineRunner iniciar(@Autowired Clientes clientes)
    {
        return args -> {
            System.out.println("Salvando clientes...");
            clientes.save(new Cliente("Rafael Souto"));
            clientes.save(new Cliente("Israel Souto"));

            List<Cliente> resultado = clientes.encontrarPorNomeComSqlNativo("Rafael");
            resultado.forEach(System.out::println);

            System.out.println("Imprimindo clientes salvos...");
            List<Cliente> listaDeClientes = clientes.findAll();
            listaDeClientes.forEach(System.out::println);

            System.out.println("Atualizando clientes...");
            listaDeClientes.forEach(c -> {c.setNome(c.getNome() + " atualizado.");
                                            clientes.save(c); //save(S entity) também suporta operação de atualização
                                        });

            System.out.println("Buscando clientes por nome...");
            clientes.findByNomeLike("Ra").forEach(System.out::println);

            System.out.println("Deletando clientes...");
            listaDeClientes = clientes.findAll();
            listaDeClientes.forEach(c -> clientes.deleteByNomeComQuery(c.getNome()));

            listaDeClientes = clientes.findAll();
            if (listaDeClientes.isEmpty()) System.out.println("Nenhum cliente encontrado");
            else  listaDeClientes.forEach(System.out::println);
        };

    }

    public static void main(String[] args)
    {
        /*Método responsável por iniciar a aplicação Spring Boot, recebe como parâmetro, a classe responsável pela inicialização da aplicação (a classe
        que possui a anotação @SpringBootAplication), e o array String fornecido pelo main().*/
        SpringApplication.run(VendasApplication.class, args);
    }
}
