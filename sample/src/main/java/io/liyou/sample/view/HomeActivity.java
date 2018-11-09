package io.liyou.sample.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.liyou.sample.R;
import io.liyou.sample.adapter.HomeAdapter;
import io.liyou.sample.contract.HomeContract;
import io.liyou.sample.model.entity.ArticleEntity;
import io.liyou.sample.model.entity.FileEntity;
import io.liyou.sample.presenter.HomePresenter;
import io.liyou.ymvp.view.base.YBaseActivity;

public class HomeActivity extends YBaseActivity<HomePresenter> implements HomeContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvFolder)
    RecyclerView rvFolder;
    @BindView(R.id.fragParentLayout)
    FrameLayout fragParentLayout;
    private ProgressDialog dialog;
    private HomeAdapter adapter;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    public static void launch(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void onInit() {
        toolbar.setTitle(R.string.xmvp_folder);
        setSupportActionBar(toolbar);
        rvFolder.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomeAdapter(new ArrayList<FileEntity>());
        rvFolder.setAdapter(adapter);
        presenter.loadData(this);
    }

    @Override
    public void onListener() {
        adapter.setOpenLister(new HomeAdapter.OpenListener() {
            @Override
            public void open(File file) {
                showCode(file);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 显示代码<br /> show code detail
     *
     * @param file
     */
    private void showCode(File file) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        CodeFragment cf = CodeFragment.newInstance(file);
        ft.replace(R.id.fragParentLayout, cf);
        ft.addToBackStack(null);
        ft.commit();
        showBlack(file.getName());
//        rvFolder.setVisibility(View.GONE);
        fragParentLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 显示返回按钮和标题，传入null则隐藏返回按钮 <br />
     * show back button and title, hide back when passing in null
     *
     * @param title
     */
    private void showBlack(String title) {
        if (title != null) {
            toolbar.setNavigationIcon(R.drawable.ic_navigate_before_black_24dp);
            toolbar.setTitle(title);
            appBarLayout.setExpanded(true, true);
        } else {
            toolbar.setNavigationIcon(null);
            toolbar.setTitle(R.string.xmvp_folder);
            fragParentLayout.setVisibility(View.GONE);
        }

    }


    @Override
    public void loadStart() {
        dialog = new ProgressDialog(this);
        dialog.setTitle("loading data...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void loadEnd(List<FileEntity> fileEntities) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        adapter.getData().addAll(fileEntities);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadArticle(List<ArticleEntity> articleEntities) {

    }


    @Override
    public void onBackPressed() {
        if (fragParentLayout.getVisibility() == View.VISIBLE) {
            showBlack(null);
        }
        super.onBackPressed();
    }
}
