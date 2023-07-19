package io.github.RafaelA11y;

import io.github.RafaelA11y.domain.entity.Cliente;
import io.github.RafaelA11y.domain.entity.Produto;
import io.github.RafaelA11y.domain.entity.Usuario;
import io.github.RafaelA11y.domain.repository.Clientes;
import io.github.RafaelA11y.domain.repository.Produtos;
import io.github.RafaelA11y.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

/*A anotação @SpringBootAplication é a anotação responsável para definir esta classe como a classe responsável por iniciar a aplicação Sping Boot
* 3º passo estender a classe SpringBootServletInitializer define a web service como uma aplicação web e não mais stand alone para que o arquivo war
* seja gerado.
* Arquivo .war é o arquivo que é injetado num servidor externo para ser publicado na internet. Para gerar o arquivp war, é necessário estar no prompt de
* comando, dentro da pasta do projeto e usar o comando 'mvn clean package' para gerar o war que é o arquivo da nossa aplicação web.
* Caso o arquivo gerado seja um jar, entre na pasta target e execute o arquivo vendas-1.0-SNAPSHOT.jar com o comando java -jar, para gerar o .jar
* basta executar o comando 'mvn clean package' na pasta raiz do projeto, não é necessário os vários passos para gerar o war*/
@SpringBootApplication
public class VendasApplication extends SpringBootServletInitializer
{
// O @Bean serve para o Spring executar o método.
    @Bean
    public CommandLineRunner commandLineRunner(@Autowired JwtService service)
    {
        return args ->
        {
            Usuario user = Usuario.builder().loguin("Fulano").build();
            System.out.println("TUDO OK!");
        };
    }

    public static void main(String[] args)
    {
        /*Método responsável por iniciar a aplicação Spring Boot, recebe como parâmetro, a classe responsável pela inicialização da aplicação (a classe
        que possui a anotação @SpringBootAplication), e o array String fornecido pelo main().*/
        SpringApplication.run(VendasApplication.class, args);
    }
}
