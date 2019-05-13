package chs.wechat.spy.controller.impl;

import chs.wechat.spy.db.mybatis.mapper.MsgFileMapper;
import chs.wechat.spy.db.mybatis.model.MsgFile;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MsgFileImpl {
    @Resource
    MsgFileMapper msgFileMapper;

    public int deleteByPrimaryKey(String id) {
        return msgFileMapper.deleteByPrimaryKey(id);
    }

    public int insert(MsgFile record) {
        return msgFileMapper.insert(record);
    }

    public int insertSelective(MsgFile record) {
        return msgFileMapper.insertSelective(record);
    }

    public MsgFile selectByPrimaryKey(String id) {
        return msgFileMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(MsgFile record) {
        return msgFileMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(MsgFile record) {
        return msgFileMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(MsgFile record) {
        return msgFileMapper.updateByPrimaryKey(record);
    }
}
