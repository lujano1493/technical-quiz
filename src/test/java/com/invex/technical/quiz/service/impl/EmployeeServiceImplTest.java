package com.invex.technical.quiz.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.invex.technical.quiz.constant.Constant;
import com.invex.technical.quiz.constant.ValidationMessageConstant;
import com.invex.technical.quiz.dto.EmployeeDto;
import com.invex.technical.quiz.dto.response.Response;
import com.invex.technical.quiz.dto.response.ResponseData;
import com.invex.technical.quiz.enums.Gender;
import com.invex.technical.quiz.exception.BusinessLogicException;
import com.invex.technical.quiz.mapper.struct.EmployeeMapStruct;
import com.invex.technical.quiz.message.MessageProvider;
import com.invex.technical.quiz.model.EmployeeEntity;
import com.invex.technical.quiz.repository.EmployeeRepository;

/**
 * The Class EmployeeServiceImplTest.
 */
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

	/** The employee repository. */
	@Mock
	private EmployeeRepository employeeRepository;

	/** The employee map struct. */
	@Mock
	private EmployeeMapStruct employeeMapStruct;

	/** The message provider. */
	@Mock
	private MessageProvider messageProvider;

	/** The employee service. */
	@InjectMocks
	private EmployeeServiceImpl employeeService;

	/** The employee dto. */
	private EmployeeDto employeeDto;
	
	/** The employee entity. */
	private EmployeeEntity employeeEntity;

	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {
		employeeDto = EmployeeDto.builder().id(1l).firstName("Jhon").secondName("Brando").lastName("Scott")
				.maternalLastName(null).age(34).dateOfBirth(LocalDate.of(1990, 10, 21)).gender(Gender.male)
				.position("Projet Manager").build();

		employeeEntity =  EmployeeEntity.builder().id(1l).firstName("Jhon").secondName("Brando").lastName("Scott")
				.maternalLastName(null).age(34).dateOfBirth(LocalDate.of(1990, 10, 21)).gender(Gender.male)
				.position("Projet Manager").build();
	}

	/**
	 * Test create employee.
	 */
	@Test
	void testCreateEmployee() {
		when(employeeMapStruct.dtoToEntity(any())).thenReturn(employeeEntity);
		when(employeeRepository.saveAll(any())).thenReturn(List.of(employeeEntity));
		when(employeeMapStruct.entityToDto(any())).thenReturn(employeeDto);

		var response = employeeService.create(List.of(employeeDto));

		assertNotNull(response);
		assertEquals(1, response.getResults().size());
	}
	
	
	
	/**
	 * Test update employee success.
	 */
	@Test
	void testUpdateEmployee_Success() {
		String successMsg="Employee updated successfully.";
		when(employeeRepository.existsById(anyLong())).thenReturn(true);
		when(employeeMapStruct.dtoToEntity(any())).thenReturn(employeeEntity);
		when(employeeMapStruct.entityToDto(any())).thenReturn(employeeDto);
		when(messageProvider.getMessage(anyString())).thenReturn(successMsg);
		when(employeeRepository.save(any())).thenReturn(employeeEntity);

		ResponseData<EmployeeDto>  response= employeeService.update(employeeDto);

		assertNotNull(response);
		assertEquals(response.getCode(), Constant.RESPONSE_OK);
		assertEquals(response.getMessage(), successMsg);
	}
	
	
	/**
	 * Test update employee error calculated age.
	 */
	@Test
	void testUpdateEmployee_Error_CalculatedAge() {
		String errorBusinessMsg="Occurred an error by processing request.";
		String errorBusinessAgeDetails="Age not match date of birth.";
		employeeDto.setAge(56);
		when(employeeRepository.existsById(anyLong())).thenReturn(true);
		when(messageProvider.getMessage(ValidationMessageConstant.EMPLOYEE_ERROR_BUSINESS_LOGIC)).thenReturn(errorBusinessMsg);
		when(messageProvider.getMessage(ValidationMessageConstant.EMPLOYEE_ERROR_BUSINESS_LOGIC_AGE_DETAILS)).thenReturn(errorBusinessAgeDetails);
		BusinessLogicException ex = assertThrows(BusinessLogicException.class, () -> {
			employeeService.update(employeeDto);
		});


		assertNotNull(ex);
		assertEquals(errorBusinessMsg  , ex.getMessage());
		assertEquals(errorBusinessAgeDetails  , ex.getDetails());
	}

	/**
	 * Test update employee not found.
	 */
	@Test
	void testUpdateEmployee_NotFound() {
		when(employeeRepository.existsById(anyLong())).thenReturn(false);
		when(messageProvider.getMessage(anyString())).thenReturn("Employee not found");

		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			employeeService.update(employeeDto);
		});

		assertTrue(exception.getMessage().contains("Employee not found"));
	}

	/**
	 * Test delete employee success.
	 */
	@Test
	void testDeleteEmployee_Success() {
		when(employeeRepository.existsById(anyLong())).thenReturn(true);
		doNothing().when(employeeRepository).deleteById(anyLong());
		when(messageProvider.getMessage(anyString())).thenReturn("Employee deleted successfully");

		Response response = employeeService.delete(1L);

		assertNotNull(response);
		assertEquals(Constant.RESPONSE_OK, response.getCode());
	}
	
	/**
	 * Test delete employee no found.
	 */
	@Test
	void testDeleteEmployee_NoFound() {
		when(employeeRepository.existsById(anyLong())).thenReturn(false);
		String notFoundMsg="Employee not Found";
		when(messageProvider.getMessage(anyString())).thenReturn(notFoundMsg);

		ResponseStatusException ex =  assertThrows(ResponseStatusException.class,   ()->employeeService.delete(1L) );

		assertNotNull(ex);
		assertEquals(HttpStatus.NOT_FOUND,   ex.getStatusCode());
		assertEquals(notFoundMsg,ex.getReason());
	}
	
	
}
