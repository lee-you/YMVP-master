package io.liyou.sample.general;

/**
 * 通用请求监听 <br/>
 * Generic request listener
 * Created by jiaji on 2018/1/30.
 */

public interface RequestListener<T>
{
    void requestSuccess(T t);

    void requestErr(String errStr);

    default void requestEnd(){

    }

}
