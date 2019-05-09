package chs.wechat.spy.mybatis.mapper;

import chs.wechat.spy.mybatis.model.WechatUser;
import chs.wechat.spy.mybatis.model.WechatUserWithBLOBs;

public interface WechatUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(WechatUserWithBLOBs record);

    int insertSelective(WechatUserWithBLOBs record);

    WechatUserWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WechatUserWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(WechatUserWithBLOBs record);

    int updateByPrimaryKey(WechatUser record);
}