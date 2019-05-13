package chs.wechat.spy.db.mybatis.mapper;

import chs.wechat.spy.db.mybatis.model.Contact;
import chs.wechat.spy.db.mybatis.model.ContactWithBLOBs;

import java.util.Map;

public interface ContactMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContactWithBLOBs record);

    int insertSelective(ContactWithBLOBs record);

    ContactWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContactWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ContactWithBLOBs record);

    int updateByPrimaryKey(Contact record);

    String getContactById(Map<String, String> identify);
}