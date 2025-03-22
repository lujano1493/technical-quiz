package com.invex.technical.quiz.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.invex.technical.quiz.config.validation.validators.ExistsInDatabaseValidator;

/**
 * The Interface ExistsInDatabase.
 */
@Constraint(validatedBy = ExistsInDatabaseValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsInDatabase {
    
    /**
     * Message.
     *
     * @return the string
     */
    String message() default "the id no exist in database.";
    
    /**
     * Groups.
     *
     * @return the class[]
     */
    Class<?>[] groups() default {};
    
    /**
     * Payload.
     *
     * @return the class<? extends payload>[]
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Entity.
     *
     * @return the class
     */
    Class<?> entity();
    
    /**
     * Field.
     *
     * @return the string
     */
    String field();
}