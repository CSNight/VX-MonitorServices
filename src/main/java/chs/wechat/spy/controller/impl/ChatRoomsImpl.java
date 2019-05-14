package chs.wechat.spy.controller.impl;

import chs.wechat.spy.db.mybatis.mapper.ChatRoomsMapper;
import chs.wechat.spy.db.mybatis.model.ChatRooms;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class ChatRoomsImpl {
    @Resource
    ChatRoomsMapper chatRoomsMapper;

    public int deleteByPrimaryKey(String id) {
        return chatRoomsMapper.deleteByPrimaryKey(id);
    }

    public int insert(ChatRooms record) {
        return chatRoomsMapper.insert(record);
    }

    public int insertSelective(ChatRooms record) {
        return chatRoomsMapper.insertSelective(record);
    }

    public ChatRooms selectByPrimaryKey(String id) {
        return chatRoomsMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(ChatRooms record) {
        return chatRoomsMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(ChatRooms record) {
        return chatRoomsMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(ChatRooms record) {
        return chatRoomsMapper.updateByPrimaryKey(record);
    }

    public String getRoomById(Map<String, String> identify) {
        return chatRoomsMapper.getRoomById(identify);
    }
}
