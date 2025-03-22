package com.invex.technical.quiz.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invex.technical.quiz.constant.Constant;
import com.invex.technical.quiz.constant.ValidationMessageConstant;
import com.invex.technical.quiz.enums.Gender;
import com.invex.technical.quiz.validation.groups.CreateValidationGroup;
import com.invex.technical.quiz.validation.groups.UpdateValidationGroup;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class EmployeeDto.
 */
@Data

/**
 * The Class EmployeeDtoBuilder.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

	/** The id. */
	@NotNull(message = ValidationMessageConstant.EMPLOYEE_ID_NOT_NULL, groups = UpdateValidationGroup.class )
	private Long id;
	
	/** The first name. */
	@NotNull(message = ValidationMessageConstant.EMPLOYEE_FIRST_NAME_NOT_NULL, groups = { CreateValidationGroup.class, UpdateValidationGroup.class } )
	@Pattern(message =  ValidationMessageConstant.EMPLOYEE_FIRST_NAME_PATTERN,regexp = Constant.REGEX_PERSON_NAME , groups = { CreateValidationGroup.class, UpdateValidationGroup.class })
	@JsonProperty(Constant.JSON_PROPERTY_EMPLOYEE_NAME_FIRST_NAME)
	private String firstName;
	
	/** The second name. */
	@Pattern(message = ValidationMessageConstant.EMPLOYEE_SECOND_NAME_PATTERN,regexp = Constant.REGEX_PERSON_NAME , groups = { CreateValidationGroup.class, UpdateValidationGroup.class })
	@JsonProperty(Constant.JSON_PROPERTY_EMPLOYEE_NAME_SECOND_NAME)
	private String secondName;
	
	/** The last name. */
	@NotNull(message =  ValidationMessageConstant.EMPLOYEE_LAST_NAME_NOT_NULL, groups = { CreateValidationGroup.class, UpdateValidationGroup.class } )
	@Pattern(message = ValidationMessageConstant.EMPLOYEE_LAST_NAME_PATTERN, regexp = Constant.REGEX_PERSON_NAME , groups = { CreateValidationGroup.class, UpdateValidationGroup.class })
	@JsonProperty(Constant.JSON_PROPERTY_EMPLOYEE_NAME_LAST_NAME)
	private String lastName;
	
	/** The maternal last name. */
	@Pattern(message =  ValidationMessageConstant.EMPLOYEE_MATERNAL_LAST_NAME_PATTERN, regexp = Constant.REGEX_PERSON_NAME , groups = { CreateValidationGroup.class, UpdateValidationGroup.class })
	@JsonProperty(Constant.JSON_PROPERTY_EMPLOYEE_NAME_MATERNAL_LAST_NAME)
	private String maternalLastName;
	
	/** The age. */
	@Min(message =  ValidationMessageConstant.EMPLOYEE_AGE_MIN, value=Constant.VALIDATION_EMPLOYEE_AGE_MIN )
	@JsonProperty(Constant.JSON_PROPERTY_EMPLOYEE_NAME_AGE)
	private Integer age;
	
	/** The gender. */
	@NotNull(message =  ValidationMessageConstant.EMPLOYEE_GENDER_NOT_NULL, groups = { CreateValidationGroup.class, UpdateValidationGroup.class } )
	@JsonProperty(Constant.JSON_PROPERTY_EMPLOYEE_NAME_GENDER)
	private Gender gender;
	
	/** The date of birth. */
	@JsonFormat(shape = Shape.STRING , pattern = Constant.NORMAL_DATE_FORMAT)
	@NotNull(message = ValidationMessageConstant.EMPLOYEE_DATE_OF_BIRTH_NOT_NULL, groups = { CreateValidationGroup.class, UpdateValidationGroup.class } )
	@JsonProperty(Constant.JSON_PROPERTY_EMPLOYEE_NAME_DATE_OF_BIRTH)
	private LocalDate dateOfBirth;
	
	/** The position. */
	@NotNull(message =  ValidationMessageConstant.EMPLOYEE_POSITION_NOT_NULL, groups = { CreateValidationGroup.class, UpdateValidationGroup.class } )
	@JsonProperty(Constant.JSON_PROPERTY_EMPLOYEE_NAME_POSITION)
	private String position;
}
