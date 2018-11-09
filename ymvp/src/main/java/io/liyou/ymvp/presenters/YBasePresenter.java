package io.liyou.ymvp.presenters;

import io.liyou.ymvp.contracts.YContract;

/**
 * MVP：Presenter基类
 */

public class YBasePresenter<T extends YContract.View, E extends YContract.Model> {
    protected T view;
    protected E model;

    @SuppressWarnings("unchecked")
    public void init(Object view, Object model) {
        this.view = (T) view;
        this.model = (E) model;
    }

    /**
     * 当Activity的onCreate或Fragment的onAttach方法执行时将会调用 <br />
     * call this method when Activity's onCreate or Fragment's onAttach method called
     */
    public void start() {}

    /**
     * 当onDestroy或onDetach方法执行时将会调用 <br />
     * call this method when Activity's onDestroy or Fragment's onDetach method called
     */
    public void end() {
        view = null;
        model = null;
    }

    /**
     * view是否还存在 <br />
     * whether view exist
     */
    public boolean viewIsExist()
    {
        return view != null;
    }
}
