
package com.invex.technical.quiz.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * The Class ResponseData.
 *
 * @param <T> the generic type
 */
@Setter
@Getter
@ToString
@SuperBuilder(builderMethodName = "dataBuilder")
public class ResponseData <T> extends Response {

	/** The data. */
	private T data;
		
}
