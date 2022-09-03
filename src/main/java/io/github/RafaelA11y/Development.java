package io.github.RafaelA11y;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/*Anotações Obrigatórias quando você cria uma notação customizada (@Target e @Retention). A constante TYPE dentro da enumeração ElementType serve para
    dizer qual o alvo da anotação será uma classe, se a anotação que estamos a criar servir para ser aplicada em um campo/atributo, então o valor correto seia
    ElementType.FIELD, mas somente uma classe pode portar a anotação @Configuration, assim é preciso definir o alvo como uma classe. A constante RUNTIME,
    dentro da enumeração RetentionPolicy serve para que a nossa anotação customizada (@development) seja retida em tempo de execução. @Target e @Retention
    são anotações obrigatórias para quando criamos nossas anotações customizadas.*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Configuration
/*@Profile serve para especificar em qual ambiente as anotações do Spring irão funcionar. Ou seja, as anotações declaradas nuam classe que também usa a
* anotação @Profile("development"), só irão ser válidas para o ambiente de desenvolvimento */
@Profile("development")
public @interface Development
{

}
