package chs.wechat.spy.db.mybatis.mapper;

import chs.wechat.spy.db.mybatis.model.PublicContact;
import chs.wechat.spy.db.mybatis.model.PublicContactWithBLOBs;

import java.util.Map;

public interface PublicContactMapper {
    int deleteByPrimaryKey(String id);

    int insert(PublicContactWithBLOBs record);

    int insertSelective(PublicContactWithBLOBs record);

    PublicContactWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PublicContactWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(PublicContactWithBLOBs record);

    int updateByPrimaryKey(PublicContact record);

    String getPublicCtById(Map<String, String> identify);
}