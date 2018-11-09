package io.liyou.sample.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tencent.mmkv.MMKV;

import butterknife.BindView;
import io.liyou.sample.R;
import io.liyou.sample.adapter.ArticleAdapter;
import io.liyou.sample.contract.ArticleDetailContract;
import io.liyou.sample.presenter.ArticlePresenter;
import io.liyou.ymvp.utils.YUtils;

/**
 * Time: 2018/11/7 0007
 * Created by LiYou
 * Description :
 */
public class ArticleListActivity extends BaseRefreshActivity<ArticleAdapter,ArticlePresenter> implements ArticleDetailContract.View {

    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @BindView(R.id.recycle)
    RecyclerView mRecycler;

    public static void launch(Context context) {
        Intent intent = new Intent(context, ArticleListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_article;
    }

    @Override
    public void onInit() {
        super.onInit();
        onRefresh();
        YUtils.makeText(this,MMKV.defaultMMKV().decodeString("uuid"));

    }

    @Override
    protected ArticleAdapter getAdapter() {
        return new ArticleAdapter();
    }

    @Override
    protected SwipeRefreshLayout getSwipeLayout() {
        return mSwipe;
    }

    @Override
    protected RecyclerView getRecyclerView() {
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        return mRecycler;
    }
}
