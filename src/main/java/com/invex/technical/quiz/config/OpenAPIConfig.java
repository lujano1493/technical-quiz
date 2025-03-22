package com.invex.technical.quiz.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.invex.technical.quiz.constant.ApiDocumentionConstant;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

/**
 * The Class OpenAPIConfig.
 */
@Configuration
public class OpenAPIConfig {

	/** The swagger information. */
	@Autowired
	private SwaggerInformation swaggerInformation;

	/**
	 * Custom open API.
	 *
	 * @return the open API
	 */
	@Bean
	OpenAPI customOpenAPI() {
		Server devServer = new Server().url(swaggerInformation.getApiDevUrl())
				.description(swaggerInformation.getDescDevServer());

		Contact contact = new Contact().email(swaggerInformation.getContactEmail())
				.name(swaggerInformation.getContactName()).url(swaggerInformation.getContactUrl());

		License mitLicense = new License().name(ApiDocumentionConstant.OPENAPI_LICENSE_NAME)
				.url(ApiDocumentionConstant.OPENAPI_LICENSE_URL);

		Info info = new Info().title(ApiDocumentionConstant.OPENAPI_DESC_TITLE)
				.version(ApiDocumentionConstant.OPENAPI_VERSION).contact(contact)
				.description(ApiDocumentionConstant.OPENAPI_DESCRIPTION)
				.termsOfService(ApiDocumentionConstant.OPENAPI_TERMS_SERVICE).license(mitLicense);

		return new OpenAPI().info(info).servers(List.of(devServer));
	}

}
