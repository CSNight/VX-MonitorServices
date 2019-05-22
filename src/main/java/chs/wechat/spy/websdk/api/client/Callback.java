package chs.wechat.spy.websdk.api.client;

import  chs.wechat.spy.websdk.api.request.ApiRequest;
import  chs.wechat.spy.websdk.api.response.ApiResponse;

import java.io.IOException;

public interface Callback<T extends ApiRequest, R extends ApiResponse> {

    void onResponse(T request, R response);

    void onFailure(T request, IOException e);

}