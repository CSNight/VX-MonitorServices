package chs.wechat.spy.mybatis.mapper;

import chs.wechat.spy.mybatis.model.Contact;
import chs.wechat.spy.mybatis.model.ContactWithBLOBs;

public interface ContactMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContactWithBLOBs record);

    int insertSelective(ContactWithBLOBs record);

    ContactWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContactWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ContactWithBLOBs record);

    int updateByPrimaryKey(Contact record);
}