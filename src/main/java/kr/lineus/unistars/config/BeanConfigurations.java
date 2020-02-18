package kr.lineus.unistars.config;


import java.io.File;
import java.util.Properties;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.DefaultObjectWrapper;




@Configuration
@EnableWebSecurity
@ComponentScan("kr.lineus")
@PropertySources({
    @PropertySource("classpath:application.properties"),
    @PropertySource(value = "file:./application_override.properties", ignoreResourceNotFound = true)
})
public class BeanConfigurations {

	@Autowired
	private MailConfig config;
	
    private static final Logger log = LoggerFactory.getLogger(BeanConfigurations.class);
    private static Directory event_index_dir = null;

    public static final String EVENT_INDEX_PATH = "lucene" + File.separator + "EventIndexDirectory";


    @Bean
    public org.springframework.web.filter.CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }

    @Bean
    @Qualifier("event")
    public Directory getDirectoryBean() {
        if (event_index_dir != null) {
            return event_index_dir;
        }
        try {
            File path = new File(EVENT_INDEX_PATH);
            if (!path.exists()) {
                path.mkdir();
            } else {
                FileSystemUtils.deleteRecursively(path);
                path.mkdir();
            }
            event_index_dir = new NIOFSDirectory(path.toPath());
            return event_index_dir;
        } catch (Exception e) {
            log.error("Unable to access lucene directory path", e);

        }
        return null;
    }

    @Bean
    public RestTemplate restTemplate() {	
        return new RestTemplate();
    }

    @Bean
    @Qualifier("fmConfig")
    public freemarker.template.Configuration getFreeMarkerConfiguration() {
        freemarker.template.Configuration config = new freemarker.template.Configuration(freemarker.template.Configuration.getVersion());

       	
        File customTemplate = new File("./templates/email");
        FileTemplateLoader ftl = null;
        if (customTemplate.exists()) {
        	try {
        		ftl = new FileTemplateLoader(customTemplate);
        	}	catch(Exception ex) {
        		ex.printStackTrace();
        	}
        }
        ClassTemplateLoader ctl = new ClassTemplateLoader(getClass(), "/templates/email");
        
        TemplateLoader[] loaders = null;
        if (ftl != null) {
          loaders = new TemplateLoader[]{ftl, ctl};
        } else {
          loaders = new TemplateLoader[]{ctl};
        }
        
        MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
        config.setTemplateLoader(mtl);
        config.setObjectWrapper(new DefaultObjectWrapper());
        config.setDefaultEncoding("UTF-8");
        config.setLocalizedLookup(false);
        config.setTemplateUpdateDelayMilliseconds(6000);
        return config;
    }
    
    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
 
        mailSender.setHost(config.EMAIL_SMTP_HOST);
        mailSender.setPort(Integer.valueOf(config.EMAIL_SMTP_PORT));
        mailSender.setUsername(config.EMAIL_SMTP_USERNAME);
        mailSender.setPassword(config.EMAIL_SMTP_PASSWORD);
        
        Properties javaMailProperties = new Properties();

        //javaMailProperties.put("mail.smtp.host", config.EMAIL_SMTP_HOST);
        //javaMailProperties.put("mail.smtp.socketFactory.port", config.EMAIL_SMTP_PORT);
        //javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        //javaMailProperties.put("mail.smtp.port", config.EMAIL_SMTP_PORT);       
        javaMailProperties.put("mail.debug", "true");

 
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }


}