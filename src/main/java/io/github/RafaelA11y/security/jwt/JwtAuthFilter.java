package io.github.RafaelA11y.security.jwt;

import io.github.RafaelA11y.service.impl.UsuarioServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*Esta classe é responsável por fazer o papel do AuthenticationManagerBuilder de efetuar a autenticação do usuário.*/
public class JwtAuthFilter extends OncePerRequestFilter
{

    private JwtService jwtService;
    private UsuarioServiceImpl usuarioService;

    public JwtAuthFilter(JwtService jwtService, UsuarioServiceImpl usuarioService)
    {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                        throws ServletException, IOException
    {

        /*Header Authorization :Bearer eyJhbGciOiJIUzUxMiJ9.
        eyJzdWIiOiJGdWxhbm8iLCJleHAiOjE2NzIyNDUwOTR9.
        UyFPuqFxsoigOEvE4Y8ordXg44FmfaL5T6zCa-SN7hXt04SnrRNKWiNp-_YHsaanvWU07Co4FxTJV7cZmYTGVA
        Captura o Header Authorization e verifica se o seu valor começa com 'Bearer'*/

        String authorization = request.getHeader("Authorization");
        if(authorization != null && authorization.startsWith("Bearer"))
        {
            /*Pega a String do token e usa o caracter de espaço para dividir a String em duas no array gerado e pega a String da segunda posição, no
            caso, pega o token da key Authorization (arrays começam a indexação a partir do 0)*/
            String token = authorization.split(" ")[1];
            //Após obter o token, verifica se este está válido
            boolean tokenIsValid = jwtService.tokenValido(token);

            if(tokenIsValid)
            {
                //Se o token ainda for válido, captura o username do usuário
                String loguinUsuario = jwtService.obterLoguinUsuario(token);
                //Carrega o usuário da base de dados num objeto User (classe User implementa UserDetails)
                UserDetails usuario = usuarioService.loadUserByUsername(loguinUsuario);
                //Token de autenticação de usuário e senha que recebe de parâmetro um UserDetail, credenciais e as authorityes
                UsernamePasswordAuthenticationToken user = new
                        UsernamePasswordAuthenticationToken(usuario,
                        null,
                        usuario.getAuthorities());
                //Informando ao Spring Security que esta autenticação é web
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //Obtendo o contexto e definindo a autenticação passando um Authentication de parâmetro.
                SecurityContextHolder.getContext().setAuthentication(user);
            }

        }
        //Despachando a requisição.
        filterChain.doFilter(request, response);

    }
}
