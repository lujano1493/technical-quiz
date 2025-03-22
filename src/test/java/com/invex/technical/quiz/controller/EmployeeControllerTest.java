package com.invex.technical.quiz.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.invex.technical.quiz.constant.Constant;
import com.invex.technical.quiz.constant.ValidationMessageConstant;
import com.invex.technical.quiz.dto.EmployeeDto;
import com.invex.technical.quiz.dto.request.ListRequestDto;
import com.invex.technical.quiz.dto.response.Response;
import com.invex.technical.quiz.dto.response.ResponseData;
import com.invex.technical.quiz.dto.response.ResponseList;
import com.invex.technical.quiz.enums.Gender;
import com.invex.technical.quiz.exception.BusinessLogicException;
import com.invex.technical.quiz.handler.GlobalExceptionHandler;
import com.invex.technical.quiz.message.MessageProvider;
import com.invex.technical.quiz.service.EmployeeService;

/**
 * The Class EmployeeControllerTest.
 */
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

	/** The mock mvc. */
	private MockMvc mockMvc;

	/** The employee service. */
	@Mock
	private EmployeeService employeeService;

	/** The message provider. */
	@Mock
	private MessageProvider messageProvider;

	/** The employee controller. */
	@InjectMocks
	private EmployeeController employeeController;

	/** The object mapper. */
	@Autowired
	private ObjectMapper objectMapper;

	/** The employee dto. */
	private EmployeeDto employeeDto;

	/** The base path. */
	private String basePath = "/employee";
	
	/** The delete path. */
	private String deletePath = "/{id}";

	/** The not found msg. */
	String notFoundMsg = "Employee not found.";
	
	/** The businesss logic msg. */
	String businesssLogicMsg = "Occurred an error by processing request.";
	
	/** The business logic age detail msg. */
	String businessLogicAgeDetailMsg = "Age not matched date of birth.";
	
	/** The argument no valid exception msg. */
	String argumentNoValidExceptionMsg="Please, valid the fields of the request.";

	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {

		employeeDto = EmployeeDto.builder().id(1l).firstName("Jhon").secondName("Brando").lastName("Scott")
				.maternalLastName(null).age(34).dateOfBirth(LocalDate.of(1990, 10, 21)).gender(Gender.male)
				.position("Projet Manager").build();

		mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
				.addPlaceholderValue("api.controllers.employee.basePath", basePath)
				.addPlaceholderValue("api.controllers.employee.delete.path", deletePath)
				.setControllerAdvice(new GlobalExceptionHandler(messageProvider)).build();

		objectMapper = new ObjectMapper();

		// 1️⃣ Configurar el formateador de fecha
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.NORMAL_DATE_FORMAT);

		// 2️⃣ Crear un módulo con el serializador personalizado
		SimpleModule module = new SimpleModule();
		module.addSerializer(LocalDate.class, new LocalDateSerializer(formatter));

		objectMapper.registerModule(new JavaTimeModule()); // Soporte para Java 8 Time API
		objectMapper.registerModule(module);
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Evita serializar como array [YYYY, MM,

	}

	/**
	 * TEST: Obtener todos los empleados.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testGetAllEmployees() throws Exception {
		ResponseList<EmployeeDto> responseList = ResponseList.<EmployeeDto>listBuilder()
				.results(Collections.singletonList(employeeDto)).build();

		when(employeeService.getAll(any(), anyInt(), anyInt())).thenReturn(responseList);

		mockMvc.perform(get(basePath).param("orderBy", "name").param("page", "1").param("size", "10"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.results[0].firstName").value("Jhon"));
	}

	/**
	 * TEST: Crear empleados *.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testCreateEmployee() throws Exception {
		ResponseList<EmployeeDto> responseList = ResponseList.<EmployeeDto>listBuilder()
				.results(Collections.singletonList(employeeDto)).build();

		when(employeeService.create(any())).thenReturn(responseList);

		ListRequestDto<EmployeeDto> requests = ListRequestDto.<EmployeeDto>builder()
				.listRequests(Collections.singletonList(employeeDto)).build();

		String content = objectMapper.writeValueAsString(requests);

		mockMvc.perform(post(basePath).contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk()).andExpect(jsonPath("$.results[0].firstName").value("Jhon"));
	}
	
	/**
	 * Test create employee bad request.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testCreateEmployee_BadRequest() throws Exception {

		configMessageErrorBadRequest();
		employeeDto.setFirstName(null);
		employeeDto.setGender(null);
		employeeDto.setDateOfBirth(null);
		ListRequestDto<EmployeeDto> requests = ListRequestDto.<EmployeeDto>builder()
				.listRequests(Collections.singletonList(employeeDto)).build();

		String content = objectMapper.writeValueAsString(requests);

		mockMvc.perform(post(basePath).contentType(MediaType.APPLICATION_JSON).content(content))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value(argumentNoValidExceptionMsg));
	}
	

	/**
	 * TEST: Actualizar empleado *.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testUpdateEmployee() throws Exception {
		ResponseData<EmployeeDto> responseData = ResponseData.<EmployeeDto>dataBuilder().data(employeeDto).build();
		when(employeeService.update(any())).thenReturn(responseData);

		mockMvc.perform(put(basePath).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employeeDto))).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.firstName").value("Jhon"));
	}
	
	/**
	 * Test update employee bad request.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testUpdateEmployee_BadRequest() throws Exception {
		configMessageErrorBadRequest();
		employeeDto.setFirstName(null);
		employeeDto.setGender(null);
		employeeDto.setDateOfBirth(null);
		employeeDto.setLastName(null);
		mockMvc.perform(put(basePath).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employeeDto)))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value(argumentNoValidExceptionMsg));
	}

	/**
	 * Test update employee error calculated age.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testUpdateEmployeeErrorCalculatedAge() throws Exception {
		configMessageErrorBusinessLogic();
		
		when(employeeService.update(any())).thenThrow(new BusinessLogicException(businesssLogicMsg,
				Constant.RESPONSE_ERROR_BUSNESS_LOGIC, businessLogicAgeDetailMsg));

		mockMvc.perform(put(basePath).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employeeDto))).andDo(print())
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message").value(businesssLogicMsg));
	}

	/**
	 * Test update employee not found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testUpdateEmployeeNotFound() throws Exception {
		configMessageNotFound();
		when(employeeService.update(any())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, notFoundMsg));

		mockMvc.perform(put(basePath).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employeeDto))).andDo(print()).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value(notFoundMsg));
	}

	/**
	 * TEST: Eliminar empleado *.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testDeleteEmployee() throws Exception {
		Response response = Response.builder().message("Deleted successfully").build();

		when(employeeService.delete(anyLong())).thenReturn(response);

		mockMvc.perform(delete(basePath + deletePath, 1L)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Deleted successfully"));
	}

	/**
	 * Test delete employee not found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testDeleteEmployee_NotFound() throws Exception {
		configMessageNotFound();
		when(employeeService.delete(anyLong()))
				.thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, notFoundMsg));

		mockMvc.perform(delete(basePath + deletePath, 1L)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value(notFoundMsg));
	}

	/**
	 * Config message not found.
	 */
	private void configMessageNotFound() {
		when(messageProvider.getMessage(notFoundMsg, notFoundMsg)).thenReturn(notFoundMsg);
	}
	
	/**
	 * Config message error business logic.
	 */
	private void configMessageErrorBusinessLogic() {
		when(messageProvider.getMessage(businesssLogicMsg, businesssLogicMsg)).thenReturn(businesssLogicMsg);
	}
	
	/**
	 * Config message error bad request.
	 */
	private void configMessageErrorBadRequest() {
		when(messageProvider.getMessage(ValidationMessageConstant.ERROR_BADREQUEST_ARG_NO_VALID)).thenReturn(argumentNoValidExceptionMsg);
	}
	

}