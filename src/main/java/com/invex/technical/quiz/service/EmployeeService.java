package com.invex.technical.quiz.service;

import java.util.List;

import com.invex.technical.quiz.dto.EmployeeDto;
import com.invex.technical.quiz.dto.response.Response;
import com.invex.technical.quiz.dto.response.ResponseData;
import com.invex.technical.quiz.dto.response.ResponseList;

/**
 * The Interface EmployeeService.
 */
public interface EmployeeService {
	
	
	/**
	 * Gets the all.
	 *
	 * @param orderBy the order by
	 * @param page the page
	 * @param size the size
	 * @return the all
	 */
	public ResponseList<EmployeeDto> getAll(String orderBy, Integer page, Integer size);
	
	/**
	 * Creates the.
	 *
	 * @param employees the employees
	 * @return the response list
	 */
	public ResponseList<EmployeeDto> create( List<EmployeeDto> employees );
	
	/**
	 * Update.
	 *
	 * @param employee the employee
	 * @return the response data
	 */
	public ResponseData<EmployeeDto> update( EmployeeDto employee ) ;
	
	/**
	 * Delete.
	 *
	 * @param id the id
	 * @return the response
	 */
	public Response delete(Long id);

}
