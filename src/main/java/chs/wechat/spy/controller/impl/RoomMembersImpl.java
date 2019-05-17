package chs.wechat.spy.controller.impl;

import chs.wechat.spy.db.mybatis.mapper.RoomMembersMapper;
import chs.wechat.spy.db.mybatis.model.RoomMembers;
import chs.wechat.spy.db.mybatis.model.RoomMembersWithBLOBs;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoomMembersImpl {
    @Resource
    RoomMembersMapper roomMembersMapper;

    public int deleteByPrimaryKey(String id) {
        return roomMembersMapper.deleteByPrimaryKey(id);
    }

    public int insert(RoomMembersWithBLOBs record) {
        return roomMembersMapper.insert(record);
    }

    public int insertSelective(RoomMembersWithBLOBs record) {
        return roomMembersMapper.insertSelective(record);
    }

    public RoomMembersWithBLOBs selectByPrimaryKey(String id) {
        return roomMembersMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(RoomMembersWithBLOBs record) {
        return roomMembersMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(RoomMembersWithBLOBs record) {
        return roomMembersMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(RoomMembers record) {
        return roomMembersMapper.updateByPrimaryKey(record);
    }

    public int truncate() {
        return roomMembersMapper.truncate();
    }
}
