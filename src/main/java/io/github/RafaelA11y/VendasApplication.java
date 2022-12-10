package io.github.RafaelA11y;

import io.github.RafaelA11y.domain.entity.Cliente;
import io.github.RafaelA11y.domain.entity.Produto;
import io.github.RafaelA11y.domain.repository.Clientes;
import io.github.RafaelA11y.domain.repository.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

/*A anotação @SpringBootAplication é a anotação responsável para definir esta classe como a classe responsável por iniciar a aplicação Sping Boot*/
@SpringBootApplication
public class VendasApplication
{
// O @Bean serve para o Spring executar o método.
    @Bean
    public CommandLineRunner commandLineRunner(@Autowired Clientes clientes)
    {
        return args ->
        {
            Cliente c = new Cliente("Rafael Souto", "06763614128");
            clientes.save(c);
        };
    }

    public static void main(String[] args)
    {
        /*Método responsável por iniciar a aplicação Spring Boot, recebe como parâmetro, a classe responsável pela inicialização da aplicação (a classe
        que possui a anotação @SpringBootAplication), e o array String fornecido pelo main().*/
        SpringApplication.run(VendasApplication.class, args);
    }
}
