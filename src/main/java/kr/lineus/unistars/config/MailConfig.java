package kr.lineus.unistars.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
    @PropertySource("classpath:application.properties"),
    @PropertySource(value = "file:./application_override.properties", ignoreResourceNotFound = true)
})
public class MailConfig {
	
	@Value("${email.smtp.host}")
	public String EMAIL_SMTP_HOST;

	@Value("${email.smtp.port}")
	public String EMAIL_SMTP_PORT;

	@Value("${email.smtp.username}")
	public String EMAIL_SMTP_USERNAME;

	@Value("${email.smtp.password}")
	public String EMAIL_SMTP_PASSWORD;
	
	@Value("${email.smtp.from.address}")
	public String EMAIL_SMTP_FROM_ADDRESS;
	
	@Value("${email.smtp.from.personalname}")
	public String EMAIL_SMTP_FROM_PERSONALNAME;
	
	@Value("${email.subject}")
	public String EMAIL_SUBJECT;

	
	
}
