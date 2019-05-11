package chs.wechat.spy.db.mybatis.mapper;

import chs.wechat.spy.db.mybatis.model.Collections;

public interface CollectionsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Collections record);

    int insertSelective(Collections record);

    Collections selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Collections record);

    int updateByPrimaryKeyWithBLOBs(Collections record);

    int updateByPrimaryKey(Collections record);
}