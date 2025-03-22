package com.invex.technical.quiz.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.invex.technical.quiz.constant.Constant;
import com.invex.technical.quiz.constant.LoggerConstant;
import com.invex.technical.quiz.constant.ValidationMessageConstant;
import com.invex.technical.quiz.exception.BusinessLogicException;
import com.invex.technical.quiz.message.MessageProvider;

import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class GlobalExceptionHandler.
 */
@RestControllerAdvice
@Slf4j
@AllArgsConstructor
public class GlobalExceptionHandler {

	/** The message provider. */
	@Autowired
	private MessageProvider messageProvider;

	/**
	 * Handle validation exceptions.
	 *
	 * @param ex the ex
	 * @return the map
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();

		BindingResult bindingResult = ex.getBindingResult();

		if (bindingResult.hasGlobalErrors()) {
			ex.getBindingResult().getGlobalErrors()
					.forEach(globalError -> errors.put(
							globalError.getCode() != null ? globalError.getCode() : globalError.getObjectName(),
							globalError.getDefaultMessage()));
		}

		if (bindingResult.hasFieldErrors()) {
			ex.getBindingResult().getFieldErrors()
					.forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
		}

		String message = messageProvider.getMessage(ValidationMessageConstant.ERROR_BADREQUEST_ARG_NO_VALID);
		response.put(Constant.RESPONSE_PROPERTY_NAME_CODE, HttpStatus.BAD_REQUEST.value());
		response.put(Constant.RESPONSE_PROPERTY_NAME_MESSAGE, message);
		response.put(Constant.RESPONSE_PROPERTY_NAME_ERRORS, errors);
		return response;
	}

	/**
	 * Handle constraint violation exception.
	 *
	 * @param ex the ex
	 * @return the map
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public Map<String, Object> handleConstraintViolationException(ConstraintViolationException ex) {
		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();

		ex.getConstraintViolations().forEach(action -> {
			errors.put(action.getPropertyPath().toString(), action.getMessage());
		});
		String message = messageProvider.getMessage(ValidationMessageConstant.ERROR_BADREQUEST_ARG_NO_VALID);
		response.put(Constant.RESPONSE_PROPERTY_NAME_CODE, HttpStatus.BAD_REQUEST.value());
		response.put(Constant.RESPONSE_PROPERTY_NAME_MESSAGE, message);
		response.put(Constant.RESPONSE_PROPERTY_NAME_ERRORS, errors);
		return response;

	}

	/**
	 * Handler http message not readable exception.
	 *
	 * @param ex the ex
	 * @return the map
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Map<String, Object> handlerHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		Map<String, Object> response = new HashMap<>();
		String message = messageProvider.getMessage(ValidationMessageConstant.ERROR_BADREQUEST_HTTP_MSG_NOT_READABLE);
		response.put(Constant.RESPONSE_PROPERTY_NAME_CODE, HttpStatus.BAD_REQUEST.value());
		response.put(Constant.RESPONSE_PROPERTY_NAME_MESSAGE, message);
		Map<String, Object> errors = new HashMap<>();
		errors.put(Constant.RESPONSE_PROPERTY_NAME_DETAILS, ex.getMessage());

		response.put(Constant.RESPONSE_PROPERTY_NAME_ERRORS, errors);
		return response;
	}

	/**
	 * Handler http message not readable exception.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<Map<String, Object>> handlerHttpMessageNotReadableException(ResponseStatusException ex) {
		Map<String, Object> response = new HashMap<>();

		String message = messageProvider.getMessage(ex.getReason(), ex.getReason());
		response.put(Constant.RESPONSE_PROPERTY_NAME_CODE, ex.getStatusCode().value());
		response.put(Constant.RESPONSE_PROPERTY_NAME_MESSAGE, message);
		response.put(Constant.RESPONSE_PROPERTY_NAME_ERRORS, null);
		return ResponseEntity.status(ex.getStatusCode()).body(response);
	}

	/**
	 * Handler business logic exception.
	 *
	 * @param ex the ex
	 * @return the map
	 */
	@ExceptionHandler(BusinessLogicException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public Map<String, Object> handlerBusinessLogicException(BusinessLogicException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put(Constant.RESPONSE_PROPERTY_NAME_CODE, ex.getCode());
		String message = messageProvider.getMessage(ex.getMessage(), ex.getMessage());
		errors.put(Constant.RESPONSE_PROPERTY_NAME_MESSAGE, message);
		errors.put(Constant.RESPONSE_PROPERTY_NAME_DETAILS, ex.getDetails());
		if (log.isDebugEnabled()) {
			log.debug(LoggerConstant.BUSINESS_LOGIC_ERROR + ex.getLocalizedMessage(), ex);
		}
		return errors;
	}

	/**
	 * Handle exception.
	 *
	 * @param ex the ex
	 * @return the map
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Map<String, Object> handleException(Exception ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put(Constant.RESPONSE_PROPERTY_NAME_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
		String message = messageProvider.getMessage(ValidationMessageConstant.ERROR_INTERNAL_SERVER_ERROR);
		String details = messageProvider.getMessage(ex.getMessage(), ex.getMessage());
		errors.put(Constant.RESPONSE_PROPERTY_NAME_MESSAGE, message);
		errors.put(Constant.RESPONSE_PROPERTY_NAME_DETAILS, details);
		log.error(LoggerConstant.UNKNOWN_ERROR, ex);
		return errors;
	}

}