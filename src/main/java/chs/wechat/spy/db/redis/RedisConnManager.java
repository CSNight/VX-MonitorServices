package chs.wechat.spy.db.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;

public class RedisConnManager {
    private String serverIp = "10.1.30.29";
    private int port = 6379;
    private String password = null;
    private boolean isBlockExhausted = true;
    private boolean jms = true;
    private boolean testOnBorrow = true;
    private long maxWaitMills = 1000 * 100;
    private int maxConn = 2000;
    private JedisPool jedisPool = null;
    private int database = 0;
    private HashMap<String, Jedis> redisClient = new HashMap<>();
    private static RedisConnManager ourInstance;

    public static RedisConnManager getInstance() {
        if (ourInstance == null) {
            synchronized (RedisConnManager.class) {
                if (ourInstance == null) {
                    ourInstance = new RedisConnManager();
                }
            }
        }
        return ourInstance;
    }

    private RedisConnManager() {
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public HashMap<String, Jedis> getRedisClient() {
        return redisClient;
    }

    public void setRedisClient(HashMap<String, Jedis> redisClient) {
        this.redisClient = redisClient;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlockExhausted() {
        return isBlockExhausted;
    }

    public void setBlockExhausted(boolean blockExhausted) {
        isBlockExhausted = blockExhausted;
    }

    public boolean isJms() {
        return jms;
    }

    public void setJms(boolean jms) {
        this.jms = jms;
    }

    public boolean isTestOnborrow() {
        return testOnBorrow;
    }

    public void setTestOnborrow(boolean testOnborrow) {
        this.testOnBorrow = testOnborrow;
    }

    public long getMaxWaitMills() {
        return maxWaitMills;
    }

    public void setMaxWaitMills(long maxWaitMills) {
        this.maxWaitMills = maxWaitMills;
    }

    public int getMaxConn() {
        return maxConn;
    }

    public void setMaxConn(int maxConn) {
        this.maxConn = maxConn;
    }


    public void BuildJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        // 连接耗尽时是否阻塞, false报异常,true阻塞直到超时, 默认true
        config.setBlockWhenExhausted(isBlockExhausted);
        // 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
        config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
        // 是否启用pool的jmx管理功能, 默认true
        config.setJmxEnabled(jms);
        // 最大连接数, 默认8个
        config.setMaxTotal(maxConn);
        // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setMaxWaitMillis(maxWaitMills);
        // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(testOnBorrow);
        jedisPool = new JedisPool(config, serverIp, port, 3000, password, database);
    }

    /**
     * 获取Jedis实例
     *
     * @return Jedis
     */
    public synchronized Jedis getJedis(String guid) {
        try {
            if (jedisPool != null) {
                Jedis jedis = jedisPool.getResource();
                redisClient.put(guid, jedis);
                return jedis;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放jedis资源
     *
     * @param guid 唯一id
     */
    public void close(String guid) {
        if (redisClient.containsKey(guid)) {
            redisClient.get(guid).close();
            redisClient.remove(guid);
        }
    }

    public void shutdown() {
        if (jedisPool != null) {
            redisClient.forEach((key, jedis) -> jedis.close());
            jedisPool.close();
            jedisPool.destroy();
        }
    }
}
