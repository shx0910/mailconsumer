package com.hongsin.mail.config.database;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.hongsin.mail.config.database.DataBaseContextHolder.DataBaseType;

@Aspect
@Component
public class ReadOnlyConnecetionInterceptor implements Ordered {

	public static final Logger logger = LoggerFactory.getLogger(ReadOnlyConnecetionInterceptor.class);
	
	@Around("@annotation(readOnlyConnection)")
	public Object proceed(ProceedingJoinPoint proceedingJoinPoint,ReadOnlyConnection readOnlyConnection) throws Throwable{
		try {
			logger.info("------------set database connection 2 read only--------------");
			DataBaseContextHolder.setDataBaseType(DataBaseType.SLAVE);
			Object result = proceedingJoinPoint.proceed();
			return result;
		}finally {
			logger.info("------------clear database connection --------------");
			DataBaseContextHolder.clearDataBaseType();
		}
	}
	
	@Override
	public int getOrder() {
		return 0;
	}

}
