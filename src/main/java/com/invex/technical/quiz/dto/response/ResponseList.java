/**
 * Self.
 *
 * @return the response list. response list builder impl
 */
package com.invex.technical.quiz.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * The Class ResponseList.
 *
 * @param <T> the generic type
 */
@Getter
@Setter
@SuperBuilder(builderMethodName = "listBuilder")
public class ResponseList <T>  extends Response{
	
	/** The results. */
	private List<T> results;
	
	/** The total elements. */
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private Long totalElements;
	
	/** The total pages. */
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private Integer totalPages;
	
	/** The number of elements. */
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private Integer numberOfElements;
	
}