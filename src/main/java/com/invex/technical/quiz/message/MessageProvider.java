package com.invex.technical.quiz.message;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * The Class MessageProvider.
 */
@Component
public class MessageProvider {

	/** The message source. */
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Gets the message.
	 *
	 * @param code the code
	 * @return the message
	 */
	public String getMessage(String code) {
		return getMessage(code, null, code);
	}

	/**
	 * Gets the message.
	 *
	 * @param code the code
	 * @param defaultMessage the default message
	 * @return the message
	 */
	public String getMessage(String code, String defaultMessage) {
		return getMessage(code, null, defaultMessage);
	}

	/**
	 * Gets the message.
	 *
	 * @param code the code
	 * @param args the args
	 * @param defaultMessage the default message
	 * @return the message
	 */
	public String getMessage(String code, Object[] args, String defaultMessage) {

		return messageSource.getMessage(code, args, defaultMessage, Locale.getDefault());
	}

}
