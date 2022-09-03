package io.github.RafaelA11y;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

/*Quando quiser criar uma configuração customizada, utilize a anotação @Configuration em cimado nome da classe. Não utilize uma classe @Configuration para
* outros fins, não use métodos ou variáveis que não irão ajudar com a configuração em sí. A anotação @Profile serve para especificar o perfil ao qual a
* classe de configuração irá ser aplicada.
* Para facilitar as configurações das classes por meio das anotações, criei a anotação @Development, essa anotação possui as anotações @Configuration e
* @Profile("development"), além de @Target e @Retention. Com anotações criadas por nós mesmos, fica mais fácil de fazer manutenção no código visto
* que, alterando a anotação customizada, a mudança é implementada em todas as classes que a implementam. Agora, os @Beans da classe MinhaConfiguration só
* serão implementados caso o perfil de desenvolvimento ativo seja o application-development.properties*/
@Development
public class MinhaConfiguration
{
    /*@Bean serve para que a gente possa exportar esse valor para fora de nossa classse e assim conseguir fazer a injeção de dependência do objeto
    * com @Autowired. Repare que o @Bean que mapeia a função tem o nome de 'applicationName'. Este mesmo nome será usado na classe VendasApplication
    * para que a injeção de dependência deste método aconteça. Lembrando que para poder definir pontos de injeção de dependência, é preciso usar o
    * @Configuration em cima da classe, caso contrário as outras anotações não irão funcionar.*/
    /*
     @Bean(name = "applicationName")
    public String applicationName()
    {
        return "Sistema de Vendas";
    }
    */
    /*Era para este método ser o primeiro a ser executado pelo Spring, mas não foi isso o que aconteceu né? :/. Quando você não define um nome para o bean,
    * por padrão, o compilador usa como nome do @Bean, o nome do próprio método (ou campo, depende de onde você está aplicando o @Bean).*/
    @Bean public CommandLineRunner executar()
    {
        return args -> {
            System.out.println("Rodando a configuração de desenvolvimento (retornando uma CommandLineRuner)!");
        };
    }

    @Bean public void executarTeste()
    {
        System.out.println("Aplicação rodando pelo perfil de desenvolvimento");
    }

}

