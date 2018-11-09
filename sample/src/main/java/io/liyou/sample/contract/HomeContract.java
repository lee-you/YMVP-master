package io.liyou.sample.contract;

import android.app.Activity;

import java.util.List;

import io.liyou.sample.general.RequestListener;
import io.liyou.sample.model.entity.ArticleEntity;
import io.liyou.sample.model.entity.FileEntity;
import io.liyou.sample.model.entity.PageEntity;
import io.liyou.ymvp.contracts.YContract;

/**
 * Created by liyou on 18-11-7.
 */

public interface HomeContract {
    interface Presenter extends YContract.Presenter {
        void loadData(Activity activity);

        void loadArticle(int start);
    }

    interface View extends YContract.View {
        void loadStart();

        void loadEnd(List<FileEntity> fileEntities);

        void loadArticle(List<ArticleEntity> articleEntities);
    }

    interface Model extends YContract.Model {
        void scanFile(final Activity activity, final RequestListener<List<FileEntity>> listener);

        void loadArticle(int start, final RequestListener<PageEntity<ArticleEntity>> listener);
    }
}
