package com.invex.technical.quiz.service.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.invex.technical.quiz.constant.Constant;
import com.invex.technical.quiz.constant.ValidationMessageConstant;
import com.invex.technical.quiz.dto.EmployeeDto;
import com.invex.technical.quiz.dto.response.Response;
import com.invex.technical.quiz.dto.response.ResponseData;
import com.invex.technical.quiz.dto.response.ResponseList;
import com.invex.technical.quiz.exception.BusinessLogicException;
import com.invex.technical.quiz.mapper.struct.EmployeeMapStruct;
import com.invex.technical.quiz.message.MessageProvider;
import com.invex.technical.quiz.model.EmployeeEntity;
import com.invex.technical.quiz.repository.EmployeeRepository;
import com.invex.technical.quiz.service.EmployeeService;
import com.invex.technical.quiz.util.PageUtils;
import com.invex.technical.quiz.util.Utils;

/**
 * The Class EmployeeServiceImpl.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	/** The employee repository. */
	@Autowired
	private EmployeeRepository employeeRepository;

	/** The employee map struct. */
	@Autowired
	private EmployeeMapStruct employeeMapStruct;

	/** The message provider. */
	@Autowired
	private MessageProvider messageProvider;

	/**
	 * Gets the all.
	 *
	 * @param orderBy the order by
	 * @param page the page
	 * @param size the size
	 * @return the all
	 */
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
	@Override
	public ResponseList<EmployeeDto> getAll(String orderBy, Integer page, Integer size) {
		PageRequest pageRequest = PageUtils.createPageRequest(orderBy, page, size);
		Page<EmployeeEntity> entities= employeeRepository.findAll(pageRequest);
		List<EmployeeDto> results = entities.stream().map(employeeMapStruct::entityToDto).toList();
		
		String resultMessage = ValidationMessageConstant.EMPLOYEE_GET_ALL;
		return _returnResults(resultMessage, results,entities);
	}

	/**
	 * Creates the.
	 *
	 * @param employees the employees
	 * @return the response list
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public ResponseList<EmployeeDto> create(List<EmployeeDto> employees) {

		_validAndCalculateAge(employees);
		List<EmployeeEntity> entities = employees.stream().map(employeeMapStruct::dtoToEntity).toList();

		employeeRepository.saveAll(entities);
		List<EmployeeDto> results = entities.stream().map(employeeMapStruct::entityToDto).toList();

		return _returnResults(ValidationMessageConstant.EMPLOYEE_CREATE_ALL, results,null);
	}

	/**
	 * Update.
	 *
	 * @param employee the employee
	 * @return the response data
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public ResponseData<EmployeeDto> update(EmployeeDto employee) {
		if (!employeeRepository.existsById(employee.getId())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					messageProvider.getMessage(ValidationMessageConstant.EMPLOYEE_NOT_FOUND));
		}

		_validAndCalculateAge(List.of(employee));
		EmployeeEntity employeeEntity = employeeMapStruct.dtoToEntity(employee);
		
		employeeRepository.save(employeeEntity);

		return ResponseData.<EmployeeDto>dataBuilder().code(Constant.RESPONSE_OK)
				.message(messageProvider.getMessage(ValidationMessageConstant.EMPLOYEE_UPDATE))
				.data(employeeMapStruct.entityToDto(employeeEntity)).build();
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 * @return the response
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Response delete(Long id) {
		if (!employeeRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					messageProvider.getMessage(ValidationMessageConstant.EMPLOYEE_NOT_FOUND));
		}
		employeeRepository.deleteById(id);

		return Response.builder().code(Constant.RESPONSE_OK)
				.message(messageProvider.getMessage(ValidationMessageConstant.EMPLOYEE_DELETE)).build();
	}

	/**
	 * Return results.
	 *
	 * @param resultMessage the result message
	 * @param results the results
	 * @param page the page
	 * @return the response list
	 */
	private ResponseList<EmployeeDto> _returnResults(String resultMessage, List<EmployeeDto> results, Page<?> page  ) {
		return ResponseList.<EmployeeDto>listBuilder().code(Constant.RESPONSE_OK)
				.message(messageProvider.getMessage(resultMessage))
				.results(results)
				.totalPages(page != null ? page.getTotalPages():0)
				.totalElements(page != null ? page.getTotalElements() :0)
				.numberOfElements( page != null ? page.getNumberOfElements() :0 )
				.build();
	}

	/**
	 * Valid and calculate age.
	 *
	 * @param employees the employees
	 */
	private void _validAndCalculateAge(List<EmployeeDto> employees) {

		AtomicInteger index = new AtomicInteger(0);
		employees.forEach(employee -> {
			_validAndCalculateAge(employee, index.getAndIncrement());
		});
	}

	/**
	 * Valid and calculate age.
	 *
	 * @param employee the employee
	 * @param andIncrement the and increment
	 */
	private void _validAndCalculateAge(EmployeeDto employee, int andIncrement) {
		int ageCalculated = Utils.calculateAge(employee.getDateOfBirth());
		if (employee.getAge() == null) {
			employee.setAge(ageCalculated);
		} else if (ageCalculated != employee.getAge()) {
			throw new BusinessLogicException(
					messageProvider.getMessage(ValidationMessageConstant.EMPLOYEE_ERROR_BUSINESS_LOGIC),
					Constant.RESPONSE_ERROR_BUSNESS_LOGIC,
					messageProvider.getMessage(ValidationMessageConstant.EMPLOYEE_ERROR_BUSINESS_LOGIC_AGE_DETAILS));
		}

	}

}
