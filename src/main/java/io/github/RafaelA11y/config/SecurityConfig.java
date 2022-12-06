package io.github.RafaelA11y.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*Esta classe irá ter toda a configuração do Spring Security. A classe deve extender WebSecurityConfigurerAdapter para ter acesso ao método
* sobrecarregado configure(), o service(AuthenticationManagerBuilder auth) é responsável por fazer a autenticação dos usuários enquanto o
* service(HttpSecurity auth) é responsável por fazer as autorizações, exemplo: usuário cliente vai poder acessar os serviços de cliente etecetera.*/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    /*O BCriptyPasswordEncoder serve para fazer a criptogrfia das senhas e correspondências das senhas criptografadas.*/
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /*Este método define uma autenticação em memória, com usuário e um codificador de senha, seguido de um usuário e senha criptografada, finalizando
    * com a definição de papel para o usuário definido.*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication()
                .passwordEncoder(this.passwordEncoder())
                .withUser("Fulano")
                .password(passwordEncoder().encode("senha123"))
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        super.configure(http);
    }
}
