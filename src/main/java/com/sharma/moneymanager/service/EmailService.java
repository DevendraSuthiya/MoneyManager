package com.sharma.moneymanager.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	
	private final JavaMailSender mailSender;
	@Value("${spring.mail.properties.mail.smtp.from}")
	private String fromMail;
	public void sendMail(String to,String subject,String body) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(fromMail);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(body);
			mailSender.send(message);
			
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public void sendMimeMail(String to, String subject, String body) {
	    try {
	        MimeMessage message = mailSender.createMimeMessage();

	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
	        helper.setFrom(fromMail);
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(body, true); // 'true' enables HTML rendering

	        mailSender.send(message);

	    } catch (Exception e) {
	        throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
	    }
	}
	public void sendEmailWithAttachment(String to , String subject , String body , byte[] attachment , String filename) {
		try {
	        MimeMessage message = mailSender.createMimeMessage();

	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
	        helper.setFrom(fromMail);
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(body); // 'true' enables HTML rendering
	        helper.addAttachment(filename, new ByteArrayResource(attachment));

	        mailSender.send(message);

	    } catch (Exception e) {
	        throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
	    }
	}
}
