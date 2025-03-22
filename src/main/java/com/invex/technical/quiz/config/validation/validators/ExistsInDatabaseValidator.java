package com.invex.technical.quiz.config.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.invex.technical.quiz.validation.annotation.ExistsInDatabase;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

/**
 * The Class ExistsInDatabaseValidator.
 */
public class ExistsInDatabaseValidator implements ConstraintValidator<ExistsInDatabase, Object> {

    /** The entity manager. */
    @PersistenceContext
    private EntityManager entityManager;

    /** The entity class. */
    private Class<?> entityClass;
    
    /** The field name. */
    private String fieldName;

    /**
     * Initialize.
     *
     * @param constraintAnnotation the constraint annotation
     */
    @Override
    public void initialize(ExistsInDatabase constraintAnnotation) {
        this.entityClass = constraintAnnotation.entity();
        this.fieldName = constraintAnnotation.field();
    }

    /**
     * Checks if is valid.
     *
     * @param value the value
     * @param context the context
     * @return true, if is valid
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; 
        }

        String queryStr = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e WHERE e." + fieldName + " = :value";
        TypedQuery<Long> query = entityManager.createQuery(queryStr, Long.class);
        query.setParameter("value", value);

        Long count = query.getSingleResult();

        return count != null && count > 0;
    }
}