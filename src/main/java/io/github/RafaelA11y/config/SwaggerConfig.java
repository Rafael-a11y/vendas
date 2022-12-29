package io.github.RafaelA11y.config;

//Classe que serve para configurar o Swagger para a documentação da API.

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration //Classe de configuração
@EnableSwagger2 //Habilita as anotações do Swagger
public class SwaggerConfig
{
    @Bean
    public Docket docket()
    {

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors
                .basePackage("io.github.RafaelA11y.rest.controller")) //Pacote onde estão as API's de cada entidade
                .paths(PathSelectors.any()).build()
                .securityContexts(Arrays.asList(securityContext())) /*Como o método recebe uma List<SecurityContext>, por isso usa-se Arrays.asList()
                                                                    para que a lista seja criada pois o método securityContext() retorna apenas um objeto*/

                .securitySchemes(Arrays.asList(apiKey())) /*O mesmo serve para o método securitySchemes(List<ApiKey>), pois o método apiKey() retorna apenas
                                                          um objeto ApiKey.*/
                .apiInfo(informacoesDaApi()); //Define um ApiInfo que representa as informações da API que são expostas no topo da UI do Swagger
    }

    private ApiInfo informacoesDaApi()
    {
        //Informações que aparecem no topo da página
        return new ApiInfoBuilder()
                .title("API de Vendas")
                .description("API do curso 'Especialista Spring Boot'").version("1.0")
                .build();
    }

    private Contact contato()
    {
        //Informações de Contato do desenvolvedor
        return new Contact("Rafael Souto da Silva", "https://github.com/Rafael-a11y", "rsowtto@gmail.com");
    }
    //Para ser possível fazer a autenticação por tokens de acesso no swagger, retorna uma ApiKey que recebe de parâmetro o nome do tipo de token,
    //o nome da chave, e onde estará a chave de autorização da requisição, neste caso no header da requisição de autenticação, ou seja: um token
    //do tipo JWT, com um header que possui uma key Authorization.
    public ApiKey apiKey()
    {
        return new ApiKey // Chave de autenticação
                ("JWT", //Nome
                "Authorization", //Keyname
                "header"); //através de um header
    }
    //Retorna um contexto de segurança usando o padrão builder.
    private SecurityContext securityContext()
    {
        return SecurityContext.builder()
                .securityReferences(defaultAuth()) // Adiciona a lista de SecurityReference auths no objeto
                .forPaths(PathSelectors.any()) //Para qualquer caminho
                .build();
    }
    //Obtêm uma Lista de SecurityReference
    private List<SecurityReference> defaultAuth()
    {
        AuthorizationScope authorizationScope = new AuthorizationScope("global",
                "acessEverything"); //Escopo de autorização definido como global que acessa qualquer coisa
        AuthorizationScope[] scopes = new AuthorizationScope[1]; //Array de AuthorizationScope com uma posição
        scopes[0] = authorizationScope; //Preenche o array com o objeto AuthorizationScope authorizationScope
        SecurityReference reference = new SecurityReference("JWT", scopes); //Cria um  SecurityReference reference  que recebe o array e um nome de referência
        List<SecurityReference> auths = new ArrayList<>(); //Cria uma lista de SecurityReference auths
        auths.add(reference); //Adiciona o SecurityReference reference dentro do ArrayList de SecurityReference auths
        return auths; //Retorna auths
    }
}
