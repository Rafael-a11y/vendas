package io.github.RafaelA11y.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/*Entidade Usuario*/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuario")
public class Usuario
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "loguin")
    @NotEmpty(message = "{campo.loguin.obrigatorio}") //Propriedade message serve para exibir uma mensagem caso a restrição não seja atendida
    private String loguin;

    @Column(name = "senha")
    @NotEmpty(message = "{campo.senha.obrigatorio}") //O valor é uma chave presente em messages.properties que contêm um valor de texto.
    private String senha;

    @Column(name = "admin")
    private boolean admin;
}
