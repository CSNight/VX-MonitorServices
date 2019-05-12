package chs.wechat.spy.controller.impl;

import chs.wechat.spy.db.mybatis.mapper.ContactMapper;
import chs.wechat.spy.db.mybatis.model.Contact;
import chs.wechat.spy.db.mybatis.model.ContactWithBLOBs;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ContactImpl {

    @Resource
    ContactMapper contactMapper;

    public int deleteByPrimaryKey(String id) {
        return contactMapper.deleteByPrimaryKey(id);
    }

    public int insert(ContactWithBLOBs record) {
        return contactMapper.insert(record);
    }

    public int insertSelective(ContactWithBLOBs record) {
        return contactMapper.insertSelective(record);
    }

    public ContactWithBLOBs selectByPrimaryKey(String id) {
        return contactMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(ContactWithBLOBs record) {
        return contactMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(ContactWithBLOBs record) {
        return contactMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(Contact record) {
        return contactMapper.updateByPrimaryKey(record);
    }
}
