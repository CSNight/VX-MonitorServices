package chs.wechat.spy.controller.impl;

import chs.wechat.spy.db.mybatis.mapper.CollectionsMapper;
import chs.wechat.spy.db.mybatis.model.Collections;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CollectionsImpl {
    @Resource
    CollectionsMapper collectionsMapper;

    public int deleteByPrimaryKey(String id) {
        return collectionsMapper.deleteByPrimaryKey(id);
    }

    public int insert(Collections record) {
        return collectionsMapper.insert(record);
    }

    public int insertSelective(Collections record) {
        return collectionsMapper.insertSelective(record);
    }

    public Collections selectByPrimaryKey(String id) {
        return collectionsMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Collections record) {
        return collectionsMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(Collections record) {
        return collectionsMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(Collections record) {
        return collectionsMapper.updateByPrimaryKey(record);
    }
}
