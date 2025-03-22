package com.invex.technical.quiz.mapper.struct;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.invex.technical.quiz.dto.EmployeeDto;
import com.invex.technical.quiz.model.EmployeeEntity;

/**
 * The Interface EmployeeMapStruct.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapStruct {

	/**
	 * Entity to dto.
	 *
	 * @param employeeEntity the employee entity
	 * @return the employee dto
	 */
	@BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
	EmployeeDto entityToDto(EmployeeEntity employeeEntity);
	
	/**
	 * Dto to entity.
	 *
	 * @param employeeDto the employee dto
	 * @return the employee entity
	 */
	@BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
	EmployeeEntity dtoToEntity(EmployeeDto employeeDto);
}
