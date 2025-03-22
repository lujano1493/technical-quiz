package com.invex.technical.quiz.dto.request;

import java.util.List;

import com.invex.technical.quiz.constant.ValidationMessageConstant;
import com.invex.technical.quiz.validation.groups.CreateValidationGroup;
import com.invex.technical.quiz.validation.groups.UpdateValidationGroup;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class ListRequestDto.
 *
 * @param <T> the generic type
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListRequestDto<T> {
	
	/** The list requests. */
	@NotEmpty(message = ValidationMessageConstant.LIST_REQUEST_NOT_EMPTY,groups = {CreateValidationGroup.class ,UpdateValidationGroup.class})
	@Valid
	private List<T> listRequests;


}

