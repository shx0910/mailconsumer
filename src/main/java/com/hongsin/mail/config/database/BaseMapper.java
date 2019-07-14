package com.hongsin.mail.config.database;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 
 * @ClassName:  BaseMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: hongxing
 * @date:   2018年4月22日 下午7:35:07   
 * @param <T>  
 * @Copyright: 2018 www.redstar.com Inc. All rights reserved. 
 *
 */
public interface BaseMapper<T> extends MySqlMapper<T>, Mapper<T> {

}
