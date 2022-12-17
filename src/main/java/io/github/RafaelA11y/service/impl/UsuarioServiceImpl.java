package io.github.RafaelA11y.service.impl;

import io.github.RafaelA11y.domain.entity.Usuario;
import io.github.RafaelA11y.domain.repository.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UserDetailsService
{
    @Autowired()
    @Lazy
    private PasswordEncoder encoder;

    @Autowired
    private Usuarios usuarios;

    @Transactional
    public Usuario salvar(Usuario usuario)
    {
        return usuarios.save(usuario);
    }

    /*Carrega o usuário da base de dados através de seu loguin. A classe User implementa UserDetails*/
    @Override()
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Usuario usuario = usuarios.findByLoguin(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado no banco de dados"));

        //Caso o usuário seja admin, retorna um vetor com os papéis de ADMIN e USER, caso contrário um vetor com String USER.
        String roles[] = usuario.isAdmin() ?
                new String[]{"USER", "ADMIN"} : new String[]{"USER"};

        return User
                .builder()
                .username(usuario
                        .getLoguin())
                .password(usuario
                        .getSenha())
                .roles(roles)
                .build();
    }
}
