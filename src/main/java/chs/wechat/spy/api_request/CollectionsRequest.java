package chs.wechat.spy.api_request;

import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.CustomHttpRequest;

import java.util.HashMap;
import java.util.Map;

public class CollectionsRequest {
    private String baseHost = ConfigProperties.GetProperties("api_host");
    private final String SYNC_COLLECTIONS = baseHost + "/fav/sync";//POST /api/fav/sync
    private final String ADD_COLLECTION = baseHost + "/fav/add";//POST /api/fav/add
    private final String SELECT_COLLECTION = baseHost + "/fav/select";//POST /api/fav/select
    private final String DELETE_COLLECTION = baseHost + "/fav/del";//POST /api/fav/del

    /**
     * @param fav_key:
     * @param uuid:
     * @return java.lang.String
     */
    public String SyncCollections(String fav_key, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("favkey", fav_key);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SYNC_COLLECTIONS, params);
    }

    /**
     * @param favObject:
     * @param uuid:
     * @return java.lang.String
     */
    public String AddCollection(String favObject, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("favObject", favObject);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(ADD_COLLECTION, params);
    }

    /**
     * @param fav_id:
     * @param uuid:
     * @return java.lang.String
     */
    public String WatchContact(String fav_id, String uuid) {

        Map<String, Object> params = new HashMap<>();
        params.put("favid", fav_id);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SELECT_COLLECTION, params);
    }

    /**
     * @param fav_id:
     * @param uuid:
     * @return java.lang.String
     */
    public String DeleteCollection(String fav_id, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("favid", fav_id);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(DELETE_COLLECTION, params);
    }
}
