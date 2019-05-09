package chs.wechat.spy.mybatis.mapper;

import chs.wechat.spy.mybatis.model.PublicContact;
import chs.wechat.spy.mybatis.model.PublicContactWithBLOBs;

public interface PublicContactMapper {
    int deleteByPrimaryKey(String id);

    int insert(PublicContactWithBLOBs record);

    int insertSelective(PublicContactWithBLOBs record);

    PublicContactWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PublicContactWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(PublicContactWithBLOBs record);

    int updateByPrimaryKey(PublicContact record);
}