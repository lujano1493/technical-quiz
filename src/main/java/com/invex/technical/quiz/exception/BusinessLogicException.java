package com.invex.technical.quiz.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The Class BusinessLogicException.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BusinessLogicException extends RuntimeException{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5724104865849012535L;
	
	/** The message. */
	private String message;
	
	/** The code. */
	private String code;
	
	/** The details. */
	private String details;

}
