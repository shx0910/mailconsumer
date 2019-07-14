package com.hongsin.mail.helper;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.hongsin.mail.constant.Const;
import com.hongsin.mail.vo.MailData;

@Service
public class GeneratorMailTemplateHelper {

	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private JavaMailSender mailSender;
	
	public void generatorAndSend(MailData data) throws MessagingException {
		Context context = new Context();
		context.setLocale(Locale.CHINA);
		context.setVariables(data.getParams());
		String templateLocation = data.getTemplateName();
		String content = templateEngine.process(templateLocation, context);
		data.setContent(content);
		send(data);
	}

	private void send(MailData data) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,Const.CHARSET_UTF8);
		helper.setFrom(data.getFrom());
		helper.setTo(data.getTo());
		helper.setSubject(data.getSubject());
		helper.setText(data.getContent(),true);
//		FileSystemResource file = new FileSystemResource(new File("G://aa.PNG"));
//		helper.addAttachment("附件1.png", file);
//		helper.addAttachment("附件2.png", file);
		mailSender.send(mimeMessage);
	}
}
