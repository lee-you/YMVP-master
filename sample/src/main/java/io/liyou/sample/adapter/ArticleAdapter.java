package io.liyou.sample.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import io.liyou.sample.R;
import io.liyou.sample.model.entity.ArticleEntity;

/**
 * Time: 2018/11/7 0007
 * Created by LiYou
 * Description :
 */
public class ArticleAdapter extends BaseQuickAdapter<ArticleEntity, BaseViewHolder> {

    public ArticleAdapter() {
        super(R.layout.item_article);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleEntity item) {
        helper.setText(R.id.tv_article_title, item.title);
    }
}
