package chs.wechat.spy.db.mybatis.mapper;

import chs.wechat.spy.db.mybatis.model.ChatRooms;

import java.util.Map;

public interface ChatRoomsMapper {
    int deleteByPrimaryKey(String id);

    int insert(ChatRooms record);

    int insertSelective(ChatRooms record);

    ChatRooms selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ChatRooms record);

    int updateByPrimaryKeyWithBLOBs(ChatRooms record);

    int updateByPrimaryKey(ChatRooms record);

    String getRoomById(Map<String, String> identify);
}