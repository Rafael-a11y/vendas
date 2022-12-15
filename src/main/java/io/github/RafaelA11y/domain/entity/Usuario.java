package io.github.RafaelA11y.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "loguin")
    @NotEmpty(message = "{campo.loguin.obrigatorio}")
    private String loguin;

    @Column(name = "senha")
    @NotEmpty(message = "{campo.senha.obrigatorio}")
    private String senha;

    @Column(name = "admin")
    private boolean admin;
}
