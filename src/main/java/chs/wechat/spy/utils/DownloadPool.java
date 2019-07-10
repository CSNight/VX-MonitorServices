package chs.wechat.spy.utils;

import chs.wechat.spy.controller.impl.ReflectUtils;
import chs.wechat.spy.controller.impl.*;
import chs.wechat.spy.db.mybatis.model.*;
import chs.wechat.spy.db.redis.RedisClientOperation;
import chs.wechat.spy.db.redis.RedisConnManager;
import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DownloadPool {
    private static DownloadPool ourInstance;
    private ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
    private RedisClientOperation rco = new RedisClientOperation();

    public static DownloadPool getInstance() {
        if (ourInstance == null) {
            synchronized (DownloadPool.class) {
                if (ourInstance == null) {
                    ourInstance = new DownloadPool();
                }
            }
        }
        return ourInstance;
    }

    private DownloadPool() {
    }

    public void StartDownloadPool() {
        scheduledThreadPool.scheduleAtFixedRate(() -> {
            RedisConnManager rcm = RedisConnManager.getInstance();
            rco.setJedisClient(rcm.getJedis(rco.getJedis_id()));
            if (rco.isKeyExit("downloadQueue")) {
                while (rco.getJedisClient().llen("downloadQueue") > 0) {
                    try {
                        String download_item = rco.getJedisClient().lpop("downloadQueue");
                        JSONObject jo_item = JSONObject.parseObject(download_item);
                        String id = jo_item.getString("id");
                        String url = jo_item.getString("url");
                        String table = jo_item.getString("table");
                        String field = jo_item.getString("field");
                        byte[] obj = CustomHttpRequest.download(url);
                        if (obj == null) {
                            continue;
                        }
                        switch (table) {
                            case "msg_file":
                                MsgFile msgFile = new MsgFile();
                                msgFile.setId(id);
                                msgFile.setFileBlob(obj);
                                ReflectUtils.getBean(MsgFileImpl.class).updateByPrimaryKeySelective(msgFile);
                                break;
                            case "chat_room":
                                ChatRooms chatRooms = new ChatRooms();
                                chatRooms.setId(id);
                                chatRooms.setSmallHead(obj);
                                ReflectUtils.getBean(ChatRoomsImpl.class).updateByPrimaryKeySelective(chatRooms);
                                break;
                            case "contact":
                                ContactWithBLOBs contactWithBLOBs = new ContactWithBLOBs();
                                contactWithBLOBs.setId(id);
                                if (field.equals("small")) {
                                    contactWithBLOBs.setSmallHead(obj);
                                } else {
                                    contactWithBLOBs.setBigHead(obj);
                                }
                                ReflectUtils.getBean(ContactImpl.class).updateByPrimaryKeySelective(contactWithBLOBs);
                                break;
                            case "public":
                                PublicContactWithBLOBs public_ct = new PublicContactWithBLOBs();
                                public_ct.setId(id);
                                if (field.equals("small")) {
                                    public_ct.setSmallHead(obj);
                                } else {
                                    public_ct.setBigHead(obj);
                                }
                                ReflectUtils.getBean(PublicContactImpl.class).updateByPrimaryKeySelective(public_ct);
                                break;
                            case "member":
                                RoomMembersWithBLOBs members = new RoomMembersWithBLOBs();
                                members.setId(id);
                                if (field.equals("small")) {
                                    members.setSmallHead(obj);
                                } else {
                                    members.setBigHead(obj);
                                }
                                ReflectUtils.getBean(RoomMembersImpl.class).updateByPrimaryKeySelective(members);
                                break;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            rcm.close(rco.getJedis_id());
        }, 1, 3, TimeUnit.SECONDS);
    }

    public void StopDownloadPool() {
        try {
            scheduledThreadPool.shutdown();
        } catch (Exception ex) {
            ex.printStackTrace();
            scheduledThreadPool.shutdownNow();
        }
    }
}
