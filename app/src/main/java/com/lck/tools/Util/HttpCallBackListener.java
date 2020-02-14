package com.lck.tools.Util;

/**
 * created by lucky on 2020/2/12
 */
public interface HttpCallBackListener {
    void onFinish(String response);
    void onError(Exception e);
}
