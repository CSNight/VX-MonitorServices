package chs.wechat.spy.redis;

import chs.wechat.spy.utils.GUID;
import chs.wechat.spy.utils.JSONUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisClientOperation {
    private Jedis jedisClient = null;


    private String jedis_id = GUID.getUUID();

    public String getJedis_id() {
        return jedis_id;
    }

    public Jedis getJedisClient() {
        return jedisClient;
    }

    public void setJedisClient(Jedis jedisClient) {
        this.jedisClient = jedisClient;
    }

    public boolean setString(String key, String json) {
        String res = jedisClient.set(key, json);
        return true;
    }

    public String getString(String key) {
        return jedisClient.get(key);
    }

    public boolean setHashTable(String hash_set, String jsonObject, boolean isOverride) {
        Map<String, String> res = jedisClient.hgetAll(hash_set);
        if (res.size() == 0 || isOverride) {
            String reply = jedisClient.hmset(hash_set, JSONUtil.json2Map(jsonObject));
            return reply.equals("OK");
        }
        return false;
    }

    public boolean setHashTable(String hash_set, Map<String, String> pojo, boolean isOverride) {
        Map<String, String> res = jedisClient.hgetAll(hash_set);
        if (res.size() == 0 || isOverride) {
            String reply = jedisClient.hmset(hash_set, pojo);
            return reply.equals("OK");
        }
        return false;
    }

    public boolean setHashField(String hash_set, String field, String value) {
        Map<String, String> res = jedisClient.hgetAll(hash_set);
        if (res.size() != 0) {
            return jedisClient.hset(hash_set, field, value) == 0;
        } else {
            return jedisClient.hset(hash_set, field, value) == 1;
        }
    }

    public boolean isKeyExit(String hash_set) {
        return jedisClient.exists(hash_set);
    }

    public String getHashTable(String hash_set) {
        Map<String, String> res = jedisClient.hgetAll(hash_set);
        if (res.size() != 0) {
            return JSONUtil.map2json(res);
        }
        return "";
    }

    public Object getHashTable(String hash_set, Class e) {
        Map<String, String> res = jedisClient.hgetAll(hash_set);
        if (res.size() != 0) {
            return JSONUtil.map2pojo(res, e);
        }
        return null;
    }

    public Map<String, String> getHashTableMap(String hash_set) {
        try {
            if (jedisClient.hgetAll(hash_set).size() != 0) {
                Object object = jedisClient.hgetAll(hash_set);
                if (object instanceof List) {
                    System.out.println(object);
                    return null;

                } else if (object instanceof Map) {
                    return (Map<String, String>) object;
                } else {
                    System.out.println(object);
                    return null;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getHashField(String hash_set, String Field) {
        Map<String, String> res = jedisClient.hgetAll(hash_set);
        if (res.size() != 0) {
            return res.get(Field);
        }
        return null;
    }

    public boolean updateHashField(String hash_set, String field, String value) {
        try {
            long res = jedisClient.hset(hash_set, field, value);
            return res == 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateHashField(String hash_set, Map<String, String> fields) {
        if (jedisClient.exists(hash_set)) {
            long res = jedisClient.hset(hash_set, fields);
            return res == 0;
        }
        return false;
    }

    public boolean updateHashNum(String hash_set, String field, long delta) {
        if (jedisClient.exists(hash_set)) {
            long res = jedisClient.hincrBy(hash_set, field, delta);
            return res == 0;
        }
        return false;
    }

    public boolean delHashField(String hash_set, String... field) {
        if (jedisClient.exists(hash_set)) {
            long res = jedisClient.hdel(hash_set, field);
            return res == 1;
        }
        return false;
    }

    public boolean deleteKey(String key) {
        if (jedisClient.exists(key)) {
            long res = jedisClient.del(key);
            return res == 0;
        }
        return false;
    }

    public boolean listadd(String key, String[] members) {
        long res = jedisClient.sadd(key, members);
        return res > 0;
    }

    public void publisher(String channel, String msg) {
        jedisClient.publish(channel, msg);
    }

    public ArrayList<String> getKeys(String pattern) {
        Set<String> res = jedisClient.keys(pattern);
        ArrayList<String> keys = new ArrayList(res);
        return keys;
    }

    public boolean saveDb() {
        if (jedisClient.isConnected()) {
            String res = jedisClient.bgsave();
            return res.equals("OK");
        }
        return false;
    }

    public boolean flushDb() {
        if (jedisClient.isConnected()) {
            String res = jedisClient.flushDB();
            return res.equals("OK");
        }
        return false;
    }

    public boolean flushAll() {
        if (jedisClient.isConnected()) {
            String res = jedisClient.flushAll();
            return res.equals("OK");
        }
        return false;
    }
}
