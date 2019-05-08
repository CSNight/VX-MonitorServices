package chs.wechat.spy.mybatis.mapper;

import chs.wechat.spy.mybatis.model.MsgFile;

public interface MsgFileMapper {
    int deleteByPrimaryKey(String id);

    int insert(MsgFile record);

    int insertSelective(MsgFile record);

    MsgFile selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MsgFile record);

    int updateByPrimaryKeyWithBLOBs(MsgFile record);

    int updateByPrimaryKey(MsgFile record);
}