package chs.wechat.spy.controller.impl;

import chs.wechat.spy.db.mybatis.mapper.PublicContactMapper;
import chs.wechat.spy.db.mybatis.model.PublicContact;
import chs.wechat.spy.db.mybatis.model.PublicContactWithBLOBs;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PublicContactImpl {
    @Resource
    PublicContactMapper publicContactMapper;

    public int deleteByPrimaryKey(String id) {
        return publicContactMapper.deleteByPrimaryKey(id);
    }

    public int insert(PublicContactWithBLOBs record) {
        return publicContactMapper.insert(record);
    }

    public int insertSelective(PublicContactWithBLOBs record) {
        return publicContactMapper.insertSelective(record);
    }

    public PublicContactWithBLOBs selectByPrimaryKey(String id) {
        return publicContactMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(PublicContactWithBLOBs record) {
        return publicContactMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(PublicContactWithBLOBs record) {
        return publicContactMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(PublicContact record) {
        return publicContactMapper.updateByPrimaryKey(record);
    }
}
