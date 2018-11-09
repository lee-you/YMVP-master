package io.liyou.sample.general;

import io.liyou.ymvp.presenters.YBasePresenter;

/**
 * Created by jiaji on 2018/1/30.
 */

public abstract class RequestListenerProxy<T> implements RequestListener<T> {
    private YBasePresenter p;

    protected RequestListenerProxy(YBasePresenter p) {
        this.p = p;
    }

    @Override
    public void requestSuccess(T t) {
        if (p.viewIsExist()) {
            success(t);
        }
    }

    @Override
    public void requestErr(String errStr) {
        if (p.viewIsExist()) {
            err(errStr);
        }
    }

    public abstract void success(T t);

    public abstract void err(String err);
}
