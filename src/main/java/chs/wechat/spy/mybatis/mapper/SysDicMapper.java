package chs.wechat.spy.mybatis.mapper;

import chs.wechat.spy.mybatis.model.SysDic;

public interface SysDicMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysDic record);

    int insertSelective(SysDic record);

    SysDic selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysDic record);

    int updateByPrimaryKey(SysDic record);
}