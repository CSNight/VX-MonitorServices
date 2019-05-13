package chs.wechat.spy.controller.impl;

import chs.wechat.spy.db.mybatis.mapper.MsgLogMapper;
import chs.wechat.spy.db.mybatis.model.MsgLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MsgLogImpl {
    @Resource
    MsgLogMapper msgLogMapper;

    public int deleteByPrimaryKey(String id) {
        return msgLogMapper.deleteByPrimaryKey(id);
    }

    public int insert(MsgLog record) {
        return msgLogMapper.insert(record);
    }

    public int insertSelective(MsgLog record) {
        return msgLogMapper.insertSelective(record);
    }

    public MsgLog selectByPrimaryKey(String id) {
        return msgLogMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(MsgLog record) {
        return msgLogMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(MsgLog record) {
        return msgLogMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(MsgLog record) {
        return msgLogMapper.updateByPrimaryKey(record);
    }
}
