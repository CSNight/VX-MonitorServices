package chs.wechat.spy.mybatis.mapper;

import chs.wechat.spy.mybatis.model.RoomMembers;
import chs.wechat.spy.mybatis.model.RoomMembersWithBLOBs;

public interface RoomMembersMapper {
    int deleteByPrimaryKey(String id);

    int insert(RoomMembersWithBLOBs record);

    int insertSelective(RoomMembersWithBLOBs record);

    RoomMembersWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RoomMembersWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(RoomMembersWithBLOBs record);

    int updateByPrimaryKey(RoomMembers record);
}