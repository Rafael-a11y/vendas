package io.github.RafaelA11y.rest.controller;

import io.github.RafaelA11y.domain.entity.Usuario;
import io.github.RafaelA11y.exception.SenhaInvalidaException;
import io.github.RafaelA11y.rest.dto.CredenciaisDTO;
import io.github.RafaelA11y.rest.dto.TokenDTO;
import io.github.RafaelA11y.security.jwt.JwtService;
import io.github.RafaelA11y.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController()
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController
{
    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    /*Retorna um Usuario e registra um novo usuário com senha criptografada no banco de dados. Sempre que um método da camada de controle for manipular
    * uma entidade validada, precisa fazer uso da anotação @Valid*/
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario)
    {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioService.salvar(usuario);
    }

    //Método que serve para autenticar o usuário (usuário já deve estar registrado no banco de dados), caso a autenticação dê certo, o loguin
    //do usuário é retornado junto de um token criptografado que vale por 30 minutos, USAR REQUISIÇÃO POST PARA AUTENTICAÇÃO É MAIS SEGURO DO QUE O USO
    //DE GET
    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciaisDTO)
    {
        try
        {
            Usuario usuario = Usuario.builder().
                    loguin(credenciaisDTO.getLoguin()).
                    senha(credenciaisDTO.getSenha()).
                    build();
           UserDetails usuarioAutenticado =  usuarioService.autenticar(usuario);
           String token = jwtService.gerarToken(usuario);
           return new TokenDTO(usuario.getLoguin(), token);
        }
        catch(UsernameNotFoundException  | SenhaInvalidaException e)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
