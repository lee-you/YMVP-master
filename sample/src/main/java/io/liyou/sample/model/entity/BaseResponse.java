package io.liyou.sample.model.entity;

/**
 * Time: 2018/11/6 0006
 * Created by LiYou
 * Description :
 */
public class BaseResponse<T> {


    public T data;
    public int errorCode;
    public String errorMsg;
}
