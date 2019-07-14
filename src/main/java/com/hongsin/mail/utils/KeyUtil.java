package com.hongsin.mail.utils;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

/**
 * 
 * @ClassName:  KeyUtil   
 * @Description:TODO(这里用一句话描述这个类的作用) 
 * <B>系统名称：</B>通用平台<BR>
 * <B>模块名称：</B>通用平台-公共服务<BR>
 * <B>中文类名：</B>KeyUtils<BR>
 * <B>概要说明：</B>主键生成策略-工具类<BR> 
 * @author: hongxing
 * @date:   2018年5月13日 下午3:41:01     
 * @Copyright: 2018 www.redstar.com Inc. All rights reserved. 
 *
 */
public class KeyUtil {

	/**
	 * <B>方法名称：</B>generatorUUID<BR>
	 * <B>概要说明：</B>主键生成策略<BR>
	 * @author hongxing
	 * @since 2018年5月13日 下午3:41:01
	 * @return UUID String
	 */
	public static String generatorUUID(){
		TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
		return timeBasedGenerator.generate().toString();
	}
	
}
