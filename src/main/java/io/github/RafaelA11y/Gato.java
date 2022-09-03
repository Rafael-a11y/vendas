package io.github.RafaelA11y;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/*Criando uma anotação customizada @Gato que puxa o @Bean (name = "gato"), da classe AnimalConfiguration (pela anotação @ Qualifier("gato"), que serve para
especificar qual @Bean será injetado pelo Spring). A anotação @Autowired, como já sabemos, serve para que o Spring inicialize o objeto atributo, neste caso,
o Spring irá inicializar o objeto Animal na Classe AnimalConfiguration, pois o @Bean com nome de 'gato', o qual @Qualifier mapeia, está assinado
sobre o método @Bean(name = 'gato') public Animal gato(), na Classe AnimalConfiguration, o objeto retornado é anónimo e implementa a inteerface
Animal, o objeto anónimo imprime no console a String 'MIAU MIAU'. O @Target({ElementType.FIELD}) serve para especificar que a anotação que criamos
(@Gato) será usada para assinar um campo, já a @Retention(RetentionPolicy.RUNTIME) serve para especificar que a anotação @Gato será retida em tempo de
execução*/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Autowired
@Qualifier("gato")
public @interface Gato {
}
