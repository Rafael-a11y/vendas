package io.github.RafaelA11y.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

/*Classes de configuração usaam @Configuration, todos os Beans de uma classe @Configuration devem ser assinados com @Bean*/
@Configuration
public class InternacionalizacaoConfig
{
    /*@Bean's servem para fazer a injeção de depndência do IOC. Este método retorna um MessageSources que representa uma fonte de mensagens, neste caso
        o message.properties que é a fonte de mensagens do nosso sistema, o método setBasename() serve para informar o nome do arquivo que comporta as
        mensagens, a palavra classpath: serve epara indicar que o arquivo está dentro do diretório da nossa aplicação, o método setDefaultEnconding()
        especifica a codificação das mensagens de messages.properties, o método setDefaultLocale especifica a localidade das mensagens de nosso sistema,
        neste caso, português brasileiro.*/
    @Bean
    public MessageSource messageSource()
    {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("ISO-8859-1");
        messageSource.setDefaultLocale(Locale.getDefault());
        return messageSource;
    }

    /*O objeto LocalValidatorFactoryBean é responsável por validar as mensagens de message.properties, isto é: fazer a troca dos valores de chave pelas
    mensagens do arquivo message.properties as quais são referenciadads pelas chaves dentro das propriedades message's, ou seja fazer a interpolação.
    o método setValidationMessageSource() define o MessageSource que contêm as mensagens de validação.*/
    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean()
    {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
