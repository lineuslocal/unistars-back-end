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
public class GeneralConfig {
	
	@Value("${errorlevel}")
	public String ERROR_LEVEL;
	
	@Value("${app.jwtSecret}")
	public String JWT_SECRET;
	
	@Value("${app.jwtExpirationMs}")
	public String JWT_EXPIRATIONMS;
}
