package com.hongsin.mail.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hongsin.mail.config.database.ReadOnlyConnection;
import com.hongsin.mail.entity.MstDict;
import com.hongsin.mail.mapper.MstDictMapper;

@Service
public class MstDictService {

	@Autowired
	private MstDictMapper mstDictMapper;
	
	@ReadOnlyConnection
	public List<MstDict> findByStatus(String status) throws Exception{
		return mstDictMapper.findByStatus(status);
	}
}
