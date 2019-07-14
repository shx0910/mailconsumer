package com.hongsin.mail.mapper;

import java.util.List;

import com.hongsin.mail.config.database.BaseMapper;
import com.hongsin.mail.entity.MstDict;

public interface MstDictMapper extends BaseMapper<MstDict> {
    
	public List<MstDict> findByStatus(String status);
}