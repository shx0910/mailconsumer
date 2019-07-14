/**
 * Copyright 2017 JINZAY All Rights Reserved.
 */
package com.hongsin.mail;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 
 * @ClassName:  MainConfig   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: hongxing
 * @date:   2018年1月13日 下午7:46:01     
 * @Copyright: 2018 www.redstar.com Inc. All rights reserved. 
 *
 */
@EnableWebMvc
@Configuration
@ComponentScan({"com.hongsin.mail.*"})
@MapperScan(basePackages = "com.hongsin.mail.mapper")
public class MainConfig {

}
