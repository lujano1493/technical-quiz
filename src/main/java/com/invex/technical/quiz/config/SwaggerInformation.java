package com.invex.technical.quiz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * The Class SwaggerInformation.
 */
@Component
@Getter
public class SwaggerInformation {

	
	/** The api dev url. */
	@Value("${sprindoc.openapi.dev.url}")
	private String apiDevUrl;
	
	/** The api pro url. */
	@Value("${sprindoc.openapi.pro.url}")
	private String apiProUrl;
	
	/** The desc dev server. */
	@Value("${sprindoc.openapi.dev.description}")
	private String descDevServer;
	
	/** The contact email. */
	@Value("${sprindoc.openapi.contact.email}")
	private String contactEmail;
	
	/** The contact name. */
	@Value("${sprindoc.openapi.contact.name}")
	private String contactName;
	
	
	/** The contact url. */
	@Value("${sprindoc.openapi.contact.url}")
	private String contactUrl;
	
	
}
