package io.liyou.ymvp.view;

import android.support.annotation.NonNull;

/**
 * Time: 2018/11/8 0008
 * Created by LiYou
 * Description :
 */
public interface IView {

    /**
     * 显示加载
     */
    default void showLoading() {

    }

    /**
     * 隐藏加载
     */
    default void hideLoading() {

    }

    /**
     * 显示信息
     *
     * @param message 消息内容, 不能为 {@code null}
     */
    default void showMessage(@NonNull String message){

    }
}
