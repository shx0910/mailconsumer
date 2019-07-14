package com.hongsin.mail.mapper;

import com.hongsin.mail.entity.MailSend;

public interface MailSend2Mapper {
    int deleteByPrimaryKey(String sendId);

    int insert(MailSend record);

    int insertSelective(MailSend record);

    MailSend selectByPrimaryKey(String sendId);

    int updateByPrimaryKeySelective(MailSend record);

    int updateByPrimaryKey(MailSend record);

	int updateByPrimaryKeyAndVersion(MailSend mailSend);
}