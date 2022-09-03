package io.github.RafaelA11y;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnimalConfiguration
{
    /*Criando um @Bean(name = cachorro), o método irá retornar um objeto anônimo que implementa a interface Animal, este objeto sobrescreve o método
    fazerBarulho()
    a String 'AU AU'.
      Um @Bean serve para declrar que o objeto assinado com @Bean deve estar disponível para as outras classes para que essas possam usá-lo por meio da
    * injeção de dependêcias do Spring Framework.*/
    @Bean(name = "cachorro") public Animal cachorro()
    {
        return new Animal()
        {
            @Override
            public void fazerBarulho()
            {
                System.out.println("AU AU");
            }
        };
    }
    /*Um @Bean serve para declrar que o objeto assinado com @Bean deve estar disponível para as outras classes para que essas possam usá-lo por meio da
    * injeção de dependêcias do Spring Framework.
    * Criando um @Bean(name = "gato") que irá retornar um objeto anônimo que implementa a interface Animal, este objeto sobrescreve o método fazerBarulho()
    * imprimindo no console a String 'MIAU MIAU'
    * */
    @Bean(name = "gato") public Animal gato()
    {
        return new Animal()
        {
            @Override
            public void fazerBarulho()
            {
                System.out.println("MIAU MIAU");
            }
        };
    }
}
