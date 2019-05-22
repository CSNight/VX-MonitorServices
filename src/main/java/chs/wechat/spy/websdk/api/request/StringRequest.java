package chs.wechat.spy.websdk.api.request;

import  chs.wechat.spy.websdk.api.response.ApiResponse;

/**
 * @author biezhi
 * @date 2018/1/18
 */
public class StringRequest extends ApiRequest<StringRequest, ApiResponse> {

    public StringRequest(String url) {
        super(url, ApiResponse.class);
    }

}