package com.foonk.Kindergarten_corporate_website.validation;

import com.foonk.Kindergarten_corporate_website.validation.impl.SubTaskListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = SubTaskListValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmptySubTaskList {
    String message() default "Должна быть хотя бы одна подзадача!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
