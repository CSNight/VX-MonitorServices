package chs.wechat.spy.controller.impl;

import chs.wechat.spy.db.mybatis.mapper.SysDicMapper;
import chs.wechat.spy.db.mybatis.model.SysDic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysDicImpl {
    @Resource
    SysDicMapper sysDicMapper;

    public int deleteByPrimaryKey(String id) {
        return sysDicMapper.deleteByPrimaryKey(id);
    }

    public int insert(SysDic record) {
        return sysDicMapper.insert(record);
    }

    public int insertSelective(SysDic record) {
        return sysDicMapper.insertSelective(record);
    }

    public SysDic selectByPrimaryKey(String id) {
        return sysDicMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(SysDic record) {
        return sysDicMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(SysDic record) {
        return sysDicMapper.updateByPrimaryKey(record);
    }
}
