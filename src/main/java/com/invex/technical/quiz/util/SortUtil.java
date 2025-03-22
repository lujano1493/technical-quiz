package com.invex.technical.quiz.util;


import org.springframework.data.domain.Sort;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Class SortUtil.
 */
public class SortUtil {

    /**
     * Builds the sort.
     *
     * @param sortParams the sort params
     * @return the sort
     */
    public static Sort buildSort(String sortParams) {
        if (sortParams == null || sortParams.isEmpty()) {
            return Sort.unsorted();
        }
        // Método para construir el objeto Sort a partir de una cadena con el formato "campo1:asc|campo2:desc"
        // Procesamos el parámetro y lo convertimos a una lista de órdenes
        return Stream.of(sortParams.split(","))
                     .map(SortUtil::getSortOrder)
                     .collect(Collectors.collectingAndThen(Collectors.toList(), Sort::by));
    }

    /**
     * Gets the sort order.
     *
     * @param sortParam the sort param
     * @return the sort order
     */
    private static Sort.Order getSortOrder(String sortParam) {
        // Método para convertir un parámetro "campo:asc" o "campo:desc" a un objeto Sort.Order
        String[] parts = sortParam.split(":");
        String field = parts[0];
        Sort.Direction direction = (parts.length > 1 && "desc".equalsIgnoreCase(parts[1]))
                                   ? Sort.Direction.DESC : Sort.Direction.ASC;
        return new Sort.Order(direction, field);
    }
}