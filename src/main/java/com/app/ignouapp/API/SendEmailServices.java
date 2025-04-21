package com.app.ignouapp.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailServices {
	
	@Autowired
	private JavaMailSender mailsender; 
	
	public void SendEmail(String name, String email) {
		String subject = "Welcome to IGNOU";
		String message = "Hello Dear, "+name+"\nYour Registration is Successfully on IGNOU.\nNow You can login through your creadentials.\n\nThank you😊😊😊\nAdmin Prince Kumar🥰";
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setSubject(subject);
		msg.setText(message);
		mailsender.send(msg);
	}
}
