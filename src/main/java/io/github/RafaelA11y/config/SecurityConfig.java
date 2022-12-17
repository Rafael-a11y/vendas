package io.github.RafaelA11y.config;

import io.github.RafaelA11y.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*Esta classe irá ter toda a configuração do Spring Security. A classe deve extender WebSecurityConfigurerAdapter para ter acesso ao método
* sobrecarregado configure(), o service(AuthenticationManagerBuilder auth) é responsável por fazer a autenticação dos usuários enquanto o
* service(HttpSecurity auth) é responsável por fazer as autorizações, exemplo: usuário cliente vai poder acessar os serviços de cliente etecetera.*/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    /*O BCriptyPasswordEncoder serve para fazer a criptogrfia das senhas e correspondências das senhas criptografadas.*/
    @Bean(name = "beanEncoder")
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /*Este método define uma autenticação em memória, com usuário e um codificador de senha, seguido de um usuário e senha criptografada, finalizando
    * com a definição de papel para o usuário definido. Foi criado um usuário Fulano com password senha123 que possui tanto papel de USER e ADMIN,
    * o método roles(String[] args) também suporta array de argumentos String. O userDetailService() carrega o usuário enquanto que o passwordEncoder()
    * compara a senha do usuário.*/
   @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    {
         auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
    }


    /*Este método define as autorizações de url's, csrf().disable serve para desativar o csrf(), antMatchers(String path, http verb) ou antMatchers
    * (String path) serve para definir a url e o método htttp ou somente a url, podemos usar em seguida .hasRole(String role) ou.hasAuthority
    * (String authority) para poder acessar a url. É importante avisar que uma role poder ter várias authorityes, o /** no caminho de /api/clientes
    indica que todos os diretórios filhos também serão acessados e caso tenha apenas um * significa que somente o nível atual do diretório está
    sendo alcançado. Também tem o método permitAll() que faz com que qualquer usuário possa acessar a url. O autheticated (funciona da seguinte
    maneira: idependente da role ou athority, qualquer usuário que esteja autenticado poderá acessar a url de antMatchers(String path). O método
    and() retorna novamente um HttpSecurity para quando for preciso aplicar mais alguma definição em cima do objeto, o método formLoguin() sem
    parâmetro faz com que o Spring forneça uma tela de loguin para o desenvolvedor poder testar a segurança, já o formLoguin(String path) com o
    caminho do arquivo.html serve para usar este formulário de loguin para ser usado para a autenticação, o arquivo html deve estar dentro de
    resources/static ou resources/public ou resources/templates. Foi definido que o usuário que tiver papel de USER ou ADMIN tem permissão para
     acessar o caminho /api/clientes/** com o método hasAnyHole(String[] roles) que aceita vários argumentos, o mesmo acontece com o caminho
     /api/pedidos/**, já o caminho /api/produtos/** pode ser acessado apenas por um usuário com perfil ADMIN, como nosso usuário Fulano em tanto
      o perfil de USER e ADMIN, ele pode acessar todos os três caminhos. Uma outra forma de configurar a autenticação é com o httpBasic() que define
     uma autenticação mais simples que é usada dentro do postman.
     O método antMatchers() também pode receber uma constante de HttpMethod para especificar o verbo http que será usado, o permitAll() serve para
     permitir que qualquer pessoa não registrada no sistema possa fazer um post de usuário (criar seu usuário). Para o caso de eu ter esquecido de
     mapear qualquer outra url, usei anyRequest().autheticated() para que o acesso de qualquer outra url deva ser feito somente após autheticação.*/




    @Override
    public void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable().authorizeRequests().antMatchers("/api/clientes/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/produtos/**").hasRole("ADMIN")
                .antMatchers("/api/pedidos/**") .hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/usuarios/**").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();
    }
}
