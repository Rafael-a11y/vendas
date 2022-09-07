package io.github.RafaelA11y;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/*A anotação @SpringBootAplication é a anotação responsável para definir esta classe como a classe responsável por iniciar a aplicação Sping Boot*/
@SpringBootApplication
/*A anotação @RestController serve para que você possa enviar mensagens para o browser */
@RestController
public class VendasApplication
{
    /*@Qualifier serve para chamar o bean correto pelo seu nome (nome este que é o parâmetro da anotação @Qualifier) o valor retornado do método
        applicationName, que pertence à classe MinhaConfiguration, irá retornar um valor, String*/
    //@Autowired
    //@Qualifier("applicationName")
    /*Usando @Value para mapear o atributo name do arquivo application.properties. Fazendo com que o valor dentro da propriedade name do arquivo application
    .properties seja injetado na String applicationName, isso ocorre por meio da experessão fornecida de parâmetro dentro de @Value*/
    @Value("${application.name}")
    private String applicationName;
    @Gato
    private Animal animal;
    /*Quando não definimos um nome para o @Bean em questão, por padrão, o nome usado é o do método, porém, existe já um @Bean assinado em cima do método
    executar(), na classe MinhaConfiguration, a classe MinhaConfiguration está assinada com @Development, ou sejka, seus @Beans só são ativados no perfil de
    desenvolvimento, caso a aplicação rode dentro do perfil de desenvolvimento, irá ser lançada ma exceção e a aplicação irá parar de funcionar, para evitar
    este problema, demos um nome para o @Bean em questão, se chama 'executarAnimal'. Dessa forma, mesmo os métodos tendo o mesmo nome, seus @Beans agora
    possuem nomes diferentes., evitando assim, o conflito de @Beans*/
    @Bean (name = "executarAnimal")
    public CommandLineRunner executar()
    {
        return args -> {this.animal.fazerBarulho();};
    }
    /*Método que será executado quando uma requisição http get for feita, o método é executado em resposta à aquisição e irá retornar uma String. Note
    * que dentro do parêntesis da anotação @GetMapping() temos o atributo path que recebe de valor uma url em forma de String, significa que este método
    * será executado quando o usuário acessar essa url, exemplo: localhost:8081/hello*/
    @GetMapping(path = "/hello")
    public String helloWorld()
    {
        return applicationName;
    }

    public static void main(String[] args)
    {
        /*Método responsável por niciar a aplicação Spring Boot, recebe como parâmetro, a classe responsável pela inicialização da aplicação (a classe
        que possui a anotação @SpringBootAplication), e o array String fornecido pelo main().*/
        SpringApplication.run(VendasApplication.class, args);
    }
}
