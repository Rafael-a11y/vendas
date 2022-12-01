package io.github.RafaelA11y.validation.constraintvalidation;

import io.github.RafaelA11y.validation.NotEmptyList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.List;

/*A anotação @NotEmptyList serve para marcar, mas quem de fato vai ser responsável por fazer a validação da anotação @NotEmptyList será a classe
* NotEmptyListValidator que implementa ConstraintValidator<NotEmptyList, List>, o primeiro parâmetro é a anotação que será implementada e o segundo
* é o tipo de dado que será validado pela anotação customizada NotEmptyList*/
public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List>
{
    /*Serve para se necessário inicializar os valores da anotação @NotEmptyList, repare qu o método initialize() inicia um objeto NotEmptyList*/
    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List value, ConstraintValidatorContext context)
    {
        return !value.isEmpty() && value != null;
    }
}
