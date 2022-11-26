package io.github.RafaelA11y.domain.entity;

import lombok.*;

import javax.persistence.*;
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
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id")
    private Integer id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;

}
