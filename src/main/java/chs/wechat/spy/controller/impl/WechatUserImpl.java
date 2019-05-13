package chs.wechat.spy.controller.impl;

import chs.wechat.spy.db.mybatis.mapper.WechatUserMapper;
import chs.wechat.spy.db.mybatis.model.WechatUser;
import chs.wechat.spy.db.mybatis.model.WechatUserWithBLOBs;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WechatUserImpl {
    @Resource
    WechatUserMapper wechatUserMapper;

    public int deleteByPrimaryKey(String id) {
        return wechatUserMapper.deleteByPrimaryKey(id);
    }

    public int insert(WechatUserWithBLOBs record) {
        return wechatUserMapper.insert(record);
    }

    public int insertSelective(WechatUserWithBLOBs record) {
        return wechatUserMapper.insertSelective(record);
    }

    public WechatUserWithBLOBs selectByPrimaryKey(String id) {
        return wechatUserMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(WechatUserWithBLOBs record) {
        return wechatUserMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(WechatUserWithBLOBs record) {
        return wechatUserMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(WechatUser record) {
        return wechatUserMapper.updateByPrimaryKey(record);
    }
}
