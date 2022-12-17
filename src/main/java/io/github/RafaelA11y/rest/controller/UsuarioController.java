package io.github.RafaelA11y.rest.controller;

import io.github.RafaelA11y.domain.entity.Usuario;
import io.github.RafaelA11y.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController
{
    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    /*Retorna um Usuario e registra um novo usuário com senha criptografada no banco de dados. Sempre que um método da camada de controle for manipular
    * uma entidade validada, precisa fazer uso da anotação @Valid*/
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario)
    {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioService.salvar(usuario);
    }
}
