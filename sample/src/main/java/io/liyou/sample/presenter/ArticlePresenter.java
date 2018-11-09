package io.liyou.sample.presenter;

import io.liyou.sample.contract.ArticleDetailContract;
import io.liyou.sample.general.RequestListener;
import io.liyou.sample.general.RequestListenerProxy;
import io.liyou.sample.model.ArticleModel;
import io.liyou.sample.model.entity.ArticleEntity;
import io.liyou.sample.model.entity.PageEntity;
import io.liyou.ymvp.presenters.YBasePresenter;

/**
 * Time: 2018/11/7 0007
 * Created by LiYou
 * Description :
 */
public class ArticlePresenter extends YBasePresenter<ArticleDetailContract.View, ArticleModel> implements ArticleDetailContract.Presenter {

    @Override
    public void requestLoadListData(int page) {
        model.loadArticleList(page, new RequestListener<PageEntity<ArticleEntity>>() {
            @Override
            public void requestSuccess(PageEntity<ArticleEntity> articleEntities) {
                view.loadListDataSuccess(articleEntities.datas, articleEntities.curPage, articleEntities.over);
            }

            @Override
            public void requestErr(String errStr) {

            }
        });
    }

    @Override
    public void requestUpdateListData() {
        model.loadArticleList(0, new RequestListenerProxy<PageEntity<ArticleEntity>>(this) {
            @Override
            public void success(PageEntity<ArticleEntity> articleEntityPageEntity) {
                view.updateListSuccess(articleEntityPageEntity.datas,articleEntityPageEntity.over);
            }

            @Override
            public void err(String err) {

            }
        });
    }
}
