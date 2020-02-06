package kr.lineus.unistars.service;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import kr.lineus.unistars.dto.Mail;

@Service("mailService")
public class MailServiceImpl implements MailService {

	 	@Autowired
	    JavaMailSender mailSender;
	 
	    @Autowired
	    @Qualifier("fmConfig")
	    freemarker.template.Configuration fmConfiguration;
	 
	    public void sendEmail(Mail mail) {

	    	
	        try {

	        	MimeMessage mimeMessage = mailSender.createMimeMessage();
	        	MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
				mimeMessageHelper.setSubject(mail.getMailSubject());
				mimeMessageHelper.setFrom(mail.getMailFrom());
				mimeMessageHelper.setTo(mail.getMailTo());
				mail.setMailContent(getContentFromTemplate(mail.getModel()));
				mimeMessageHelper.setText(mail.getMailContent(), true);

				mailSender.send(mimeMessageHelper.getMimeMessage());
			} catch (MessagingException e) {
				e.printStackTrace();
			}		
		}

	public String getContentFromTemplate(Map<String, Object> model) {
		StringBuffer content = new StringBuffer();

		try {
			content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("email-template.txt"), model));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}
	 
	    
}
