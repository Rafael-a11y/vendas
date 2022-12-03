package io.github.RafaelA11y.validation;

import io.github.RafaelA11y.validation.constraintvalidation.NotEmptyListValidator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/*A anotação @Target(ElementType.FIELD) serve para definir onde poderá ser usada a anotação, @Retention(RUNTIME)* serve para defibir que a sua
    verificação será em tempo de execução, e a @Constraint(validateBy = {NotEmptyValidator.class}) serve para definir a classe que de fato irá
    fazer a validação em tempo de execução. A anotação @Constraint valida a nossa anotação customizada como uma anotação de validação*/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotEmptyListValidator.class})
public @interface NotEmptyList
{
    //Mensagem padrão para caso o programador não defina uma message para a anotação.
    String message() default "A lista não deve ser vazia";

    /*Todas as anotações de validação possuem essas propriedades: group e payload, é necessário defini-las na interface da anotação de validação
    * para que o código compile corretamente.*/
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
