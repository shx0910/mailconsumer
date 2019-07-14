package com.hongsin.mail.task;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hongsin.mail.entity.MailSend;
import com.hongsin.mail.enumeration.RedisPriorityQueue;
import com.hongsin.mail.service.MailSendService;
import com.hongsin.mail.utils.FastJsonConvertUtil;

@Component
public class ConsumerMailTask {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private MailSendService mailSendService;
	
	@Scheduled(initialDelay = 5000, fixedDelay = 2000)
	public void intervalFast() {
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		String ret = opsForList.rightPop(RedisPriorityQueue.FAST_QUEUE.getCode());
		if(StringUtils.isNotBlank(ret)) {
			System.out.println(ret);
			MailSend mailSend = FastJsonConvertUtil.convertJSONToObject(ret, MailSend.class);
			mailSendService.sendMessage4Order(mailSend);
		}
	}
	
//	@Scheduled(initialDelay = 5000, fixedDelay = 10000)
//	public void intervalNormal() {
//		System.out.println("excutor....");
//	}
//	
//	@Scheduled(initialDelay = 5000, fixedDelay = 60000)
//	public void intervalDefer() {
//		System.out.println("excutor....");
//	}
}
