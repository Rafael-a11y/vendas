package io.github.RafaelA11y.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "produto")
// As anotações do lombok servem para a criação de métodos em tempo de compilação como get's e set's, contrutores etecetera, temos a @NoArgsConstructor
// que serve para criar um construtor sem argumento, a @AllArgsConstructor que serve para criar construtor com todos os argumentos e a @Data que serve
// para trazer as anotações @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode, e lombok.Value
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Produto
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Integer id;

    /*Campo descricao é validado por @NotEmpty que não aceita valor nulo ou em branco, o atributo message especifica a mensagem que irá aparecer caso
    o usuário tente incluir um valor nulo ou branco no campo descrição, neste caso, ao invés de as mensagens serem inseridas diretamente no atributo,
    fez-se uso de internacionalização, isto  é as mensagens de validação estão no arquivo messages.properties, e o valor da propriedade message é a
    chave que referencia a mensagem adequada dentro de message.properties. */
    @Column(name = "descricao")
    @NotEmpty(message = "{campo.descricao.obrigatorio}")
    private String descricao;

    /*Campo precoUnitario é validado por @NotNull, como se trata de uma caqmpo númerico, não existe a possibilidade de ser um valor vazio, ou é
    * prenchido, ou é nulo, a anotação @Min especifica um valor mínimo, pois como sabemos, preço negativo não faz sentido. */
    @Column(name = "preco_unitario")
    @NotNull(message = "{campo.preco.obrigatorio}")
    @Min(value = 0, message = "O preço deve ser maior ou igual a zero")
    private BigDecimal precoUnitario;

}
