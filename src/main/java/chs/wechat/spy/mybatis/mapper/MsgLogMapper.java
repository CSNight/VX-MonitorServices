package chs.wechat.spy.mybatis.mapper;

import chs.wechat.spy.mybatis.model.MsgLog;

public interface MsgLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(MsgLog record);

    int insertSelective(MsgLog record);

    MsgLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MsgLog record);

    int updateByPrimaryKeyWithBLOBs(MsgLog record);

    int updateByPrimaryKey(MsgLog record);
}