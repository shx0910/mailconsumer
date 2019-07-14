package com.hongsin.mail;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.pagehelper.PageHelper;
import com.hongsin.mail.entity.MstDict;
import com.hongsin.mail.mapper.MstDictMapper;
import com.hongsin.mail.service.MstDictService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {

	@Resource(name = "masterDataSource")
	private DataSource masterDataSource;

	@Resource(name = "slaveDataSource")
	private DataSource slaveDataSource;
	@Autowired
	private JavaMailSender mailSender;

	@Test
	public void contextLoads() throws Exception {
		Connection c1 = masterDataSource.getConnection("root", "root");
		System.out.println("c1：" + c1.getMetaData().getURL());
		Connection c2 = slaveDataSource.getConnection("root", "root");
		System.out.println("c2：" + c2.getMetaData().getURL());
	}

	@Autowired
	private MstDictMapper mstDictMapper;

	@Test
	public void test1() throws Exception {
		PageHelper.startPage(1, 2);
		List<MstDict> list = mstDictMapper.selectAll();
		for (MstDict md : list) {
			System.err.println(md.getName());
		}
	}

	@Autowired
	private MstDictService mstDictService;

	@Test
	public void test2() throws Exception {
		List<MstDict> list = mstDictService.findByStatus("1");
		if (list != null && list.size() > 0) {
			for (MstDict md : list) {
				System.err.println(md.getName());
			}
		}
	}

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	public void test3() throws Exception {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		opsForValue.set("name", "shaohongxing");
		System.out.println("name:" + opsForValue.get("name"));
	}
	
	/**
	 * 测试邮件发送
	 * @Title: sendSimpleMail   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws
	 */
	@Test
	public void sendSimpleMail() throws Exception{
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("shxxing00@sina.com");
		mailMessage.setTo("191791133@qq.com");
		mailMessage.setSubject("主题：测试邮件");
		mailMessage.setText("测试邮件内容！");
		mailSender.send(mailMessage);
	}
	
	@Test
	public void sendAttachmentsMail() throws Exception{
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("shxxing00@sina.com");
		helper.setTo("191791133@qq.com");
		helper.setSubject("主题：测试邮件");
		helper.setText("测试邮件内容！");
		FileSystemResource file = new FileSystemResource(new File("G://aa.PNG"));
		helper.addAttachment("附件1.png", file);
		helper.addAttachment("附件2.png", file);
		mailSender.send(mimeMessage);
	}
}
