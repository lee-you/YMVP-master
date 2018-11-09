package io.liyou.sample.presenter;

import android.app.Activity;

import io.liyou.sample.contract.HomeContract;
import io.liyou.sample.general.RequestListener;
import io.liyou.sample.model.HomeModel;
import io.liyou.sample.model.entity.ArticleEntity;
import io.liyou.sample.model.entity.PageEntity;
import io.liyou.ymvp.presenters.YBasePresenter;

/**
 * Created by liyou on 18-11-7.
 */

public class HomePresenter extends YBasePresenter<HomeContract.View, HomeModel> implements HomeContract.Presenter {

    @Override
    public void loadData(Activity activity) {
        view.loadStart();
    }

    @Override
    public void loadArticle(int start) {
        model.loadArticle(0, new RequestListener<PageEntity<ArticleEntity>>() {
            @Override
            public void requestSuccess(PageEntity<ArticleEntity> listBaseResponse) {

            }

            @Override
            public void requestErr(String errStr) {

            }
        });
    }
}
