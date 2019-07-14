package com.hongsin.mail.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.hongsin.mail.entity.MailSend;
import com.hongsin.mail.enumeration.MailStatus;
import com.hongsin.mail.helper.GeneratorMailTemplateHelper;
import com.hongsin.mail.mapper.MailSend1Mapper;
import com.hongsin.mail.mapper.MailSend2Mapper;
import com.hongsin.mail.vo.MailData;

@Service
public class MailSendService {

	private static Logger logger = LoggerFactory.getLogger(MailSendService.class);
	
	@Autowired
	private MailSend1Mapper mailSend1Mapper;
	@Autowired
	private MailSend2Mapper mailSend2Mapper;
	@Autowired
	private RedisTemplate<String, String>  redisTemplate;
	@Autowired
	private GeneratorMailTemplateHelper generatorMailTemplateHelper;
	
	public void sendMessage4Order(MailSend mailSend) {
		Map<String,Object> params = new HashMap<>();
		params.put("userName", mailSend.getSendUserId());
		params.put("createDate", DateFormatUtils.format(mailSend.getUpdateTime(), "yyyy-mm-dd"));
		params.put("exportUrl", "http://www.baidu.com");
		MailData mailData = new MailData();
		mailData.setUserId(mailSend.getSendUserId());
		mailData.setFrom("shxxing00@sina.com");
		mailData.setTo(mailSend.getSendTo());
		mailData.setSubject("【京东订单】");
		mailData.setTemplateName("SHEET");
		mailData.setParams(params);
		
		try {
			//模板渲染及发送
			generatorMailTemplateHelper.generatorAndSend(mailData);
			mailSend.setSendStatus(MailStatus.NEED_OK.getCode());
			int hashCode = mailSend.getSendId().hashCode();
			if (hashCode % 2 == 0) {
				mailSend2Mapper.updateByPrimaryKeyAndVersion(mailSend);
			} else {
				mailSend1Mapper.updateByPrimaryKeyAndVersion(mailSend);
			}
			logger.info("发送邮件成功,id:{},userId:{}",mailSend.getSendId(),mailSend.getSendUserId());
		} catch (MessagingException e) {
			e.printStackTrace();
			logger.error("发送邮件异常", e.getMessage());
			if(mailSend.getSendCount()>4) {
				mailSend.setSendStatus(MailStatus.NEED_ERR.getCode());
				logger.info("发送邮件失败,id:{},userId:{}",mailSend.getSendId(),mailSend.getSendUserId());
			}else {
				mailSend.setSendStatus(MailStatus.DRAFT.getCode()); 
			}
			int hashCode = mailSend.getSendId().hashCode();
			if (hashCode % 2 == 0) {
				mailSend2Mapper.updateByPrimaryKeyAndVersion(mailSend);
			} else {
				mailSend1Mapper.updateByPrimaryKeyAndVersion(mailSend);
			}
			throw new RuntimeException();
		}
	}
	
}
