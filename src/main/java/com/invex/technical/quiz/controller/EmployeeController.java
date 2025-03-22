package com.invex.technical.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.invex.technical.quiz.constant.ApiConstant;
import com.invex.technical.quiz.constant.ApiDocumentionConstant;
import com.invex.technical.quiz.dto.EmployeeDto;
import com.invex.technical.quiz.dto.request.ListRequestDto;
import com.invex.technical.quiz.dto.response.Response;
import com.invex.technical.quiz.dto.response.ResponseData;
import com.invex.technical.quiz.dto.response.ResponseList;
import com.invex.technical.quiz.service.EmployeeService;
import com.invex.technical.quiz.validation.groups.CreateValidationGroup;
import com.invex.technical.quiz.validation.groups.UpdateValidationGroup;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * The Class EmployeeController.
 */
@RestController
@RequestMapping(ApiConstant.CONTROLLER_EMPLOYEE_BASE_PATH)
@Validated
public class EmployeeController {

	/** The employee service. */
	@Autowired
	private EmployeeService employeeService;

	/**
	 * Gets the all employees.
	 *
	 * @return the all employees
	 */
	@Operation(summary = ApiDocumentionConstant.EMPLOYEE_SUMMARY_GET_ALL_EMPLOYEES, tags = ApiDocumentionConstant.EMPLOYEE_TAG)
	@ApiResponses(value = {
			@ApiResponse(responseCode = ApiDocumentionConstant.RESPONSE_CODE_200, description = ApiDocumentionConstant.EMPLOYEE_DESCRIPTION_GET_ALL_EMPLOYEES_200),
			@ApiResponse(responseCode = ApiDocumentionConstant.RESPONSE_CODE_500, description = ApiDocumentionConstant.RESPONSE_DESCRIPTION_500) })

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseList<EmployeeDto> getAll(
			@RequestParam(value = ApiConstant.CONTROLLER_REQUEST_PARAM_ORDER_BY, required = false) String orderBy,
			@Min(ApiConstant.REQUEST_PARAM_PAGE_MIN_VALUE) @RequestParam(value = ApiConstant.CONTROLLER_REQUEST_PARAM_PAGE, defaultValue = ApiConstant.REQUEST_PARAM_PAGE_DEFAULT_VALUE) Integer page,
			@Min(ApiConstant.REQUEST_PARAM_SIZE_MIN_VALUE) @Max(ApiConstant.REQUEST_PARAM_SIZE_MAX_VALUE) @RequestParam(value = ApiConstant.CONTROLLER_REQUEST_PARAM_SIZE, defaultValue = ApiConstant.REQUEST_PARAM_SIZE_DEFAULT_VALUE) Integer size) {

		return employeeService.getAll(orderBy, page, size);
	}

	/**
	 * Creates employees.
	 *
	 * @param listRequest the list request
	 * @return the response list
	 */
	@Operation(summary = ApiDocumentionConstant.EMPLOYEE_SUMMARY_CREATE_EMPLOYEES, tags = ApiDocumentionConstant.EMPLOYEE_TAG)
	@ApiResponses(value = {
			@ApiResponse(responseCode = ApiDocumentionConstant.RESPONSE_CODE_200, description = ApiDocumentionConstant.EMPLOYEE_DESCRIPTION_CREATE_EMPLOYEES_200),
			@ApiResponse(responseCode = ApiDocumentionConstant.RESPONSE_CODE_400, description = ApiDocumentionConstant.RESPONSE_DESCRIPTION_400),
			@ApiResponse(responseCode = ApiDocumentionConstant.RESPONSE_CODE_409, description = ApiDocumentionConstant.RESPONSE_DESCRIPTION_409),
			@ApiResponse(responseCode = ApiDocumentionConstant.RESPONSE_CODE_500, description = ApiDocumentionConstant.RESPONSE_DESCRIPTION_500) })

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseList<EmployeeDto> create(
			@RequestBody @Validated(CreateValidationGroup.class) ListRequestDto<EmployeeDto> listRequest) {
		return employeeService.create(listRequest.getListRequests());

	}

	/**
	 * Update employee.
	 *
	 * @param employee the employee
	 * @return the response data
	 */
	@Operation(summary = ApiDocumentionConstant.EMPLOYEE_SUMMARY_UPDATE_EMPLOYEE, tags = ApiDocumentionConstant.EMPLOYEE_TAG)
	@ApiResponses(value = {
			@ApiResponse(responseCode = ApiDocumentionConstant.RESPONSE_CODE_200, description = ApiDocumentionConstant.EMPLOYEE_DESCRIPTION_UPDATE_EMPLOYEE_200),
			@ApiResponse(responseCode = ApiDocumentionConstant.RESPONSE_CODE_400, description = ApiDocumentionConstant.RESPONSE_DESCRIPTION_400),
			@ApiResponse(responseCode = ApiDocumentionConstant.RESPONSE_CODE_404, description = ApiDocumentionConstant.RESPONSE_DESCRIPTION_404),
			@ApiResponse(responseCode = ApiDocumentionConstant.RESPONSE_CODE_409, description = ApiDocumentionConstant.RESPONSE_DESCRIPTION_409),
			@ApiResponse(responseCode = ApiDocumentionConstant.RESPONSE_CODE_500, description = ApiDocumentionConstant.RESPONSE_DESCRIPTION_500) })

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseData<EmployeeDto> update(@RequestBody @Validated(UpdateValidationGroup.class) EmployeeDto employee) {
		return employeeService.update(employee);
	}

	/**
	 * Delete umployee by id.
	 *
	 * @param id the id
	 * @return the response
	 */
	@Operation(summary = ApiDocumentionConstant.EMPLOYEE_SUMMARY_DELETE_EMPLOYEE, tags = ApiDocumentionConstant.EMPLOYEE_TAG)
	@ApiResponses(value = {
			@ApiResponse(responseCode = ApiDocumentionConstant.RESPONSE_CODE_200, description = ApiDocumentionConstant.EMPLOYEE_DESCRIPTION_DELETE_EMPLOYEE_200),
			@ApiResponse(responseCode = ApiDocumentionConstant.RESPONSE_CODE_400, description = ApiDocumentionConstant.RESPONSE_DESCRIPTION_400),
			@ApiResponse(responseCode = ApiDocumentionConstant.RESPONSE_CODE_500, description = ApiDocumentionConstant.RESPONSE_DESCRIPTION_500) })

	@DeleteMapping(ApiConstant.CONTROLLER_EMPLOYEE_DELETE_PATH)
	@ResponseStatus(HttpStatus.OK)
	public Response delete(@PathVariable(ApiConstant.CONTROLLER_EMPLOYEE_DELETE_PATH_VARIABLE ) Long id) {
		return employeeService.delete(id);
	}

}
