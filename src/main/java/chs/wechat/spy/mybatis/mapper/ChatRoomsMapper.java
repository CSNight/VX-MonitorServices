package chs.wechat.spy.mybatis.mapper;

import chs.wechat.spy.mybatis.model.ChatRooms;

public interface ChatRoomsMapper {
    int deleteByPrimaryKey(String id);

    int insert(ChatRooms record);

    int insertSelective(ChatRooms record);

    ChatRooms selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ChatRooms record);

    int updateByPrimaryKeyWithBLOBs(ChatRooms record);

    int updateByPrimaryKey(ChatRooms record);
}