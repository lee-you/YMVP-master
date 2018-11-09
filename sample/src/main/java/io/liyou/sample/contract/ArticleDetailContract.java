package io.liyou.sample.contract;

import io.liyou.sample.general.RequestListener;
import io.liyou.sample.model.entity.ArticleEntity;
import io.liyou.sample.model.entity.PageEntity;
import io.liyou.ymvp.contracts.YContract;

/**
 * Time: 2018/11/7 0007
 * Created by LiYou
 * Description :
 */
public interface ArticleDetailContract {

    interface View extends BaseRefreshContract.View {
    }

    interface Presenter extends BaseRefreshContract.Presenter {
    }

    interface Model extends YContract.Model {
        void loadArticleList(int page, RequestListener<PageEntity<ArticleEntity>> listener);
    }

}
