/*
 * Copyright 2016 XuJiaji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.liyou.ymvp.view.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.liyou.ymvp.R;
import io.liyou.ymvp.presenters.YBasePresenter;
import io.liyou.ymvp.utils.GenericHelper;
import io.liyou.ymvp.view.MultipleStatusView;
import io.liyou.ymvp.view.interfaces.YActivityCycle;

/**
 * 项目中Activity的基类 <br />
 * base Activity class
 */
public abstract class YBaseActivity<T extends YBasePresenter> extends AppCompatActivity implements YActivityCycle<T> {
    protected T presenter;
    private Unbinder mUnBinder;
    private ProgressDialog dialog;
    public MultipleStatusView mMultipleStatusView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        onBeforeCreate();
        try {
            presenter = GenericHelper.newPresenter(this);
            if (presenter != null) {
                presenter.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        onPresenterCircle(presenter);
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            // 页面已被启用，但因内存不足页面被系统销毁过
            // page start when the page was destroyed by the system due to insufficient memory
            onBundleHandle(savedInstanceState);
        } else {
            // 第一次进入页面获取上个页面传递过来的数据
            // handle intent when first enter the page
            Intent intent = getIntent();
            if (intent != null) {
                onIntentHandle(intent);
            }
        }

        if (layoutId() != 0) {
            setContentView(layoutId());
            //绑定到butterknife
            mUnBinder = ButterKnife.bind(this);
            mMultipleStatusView = findViewById(R.id.multiple_status_view);
        }

        onInit();
        onListener();
    }

    // 非standard的启动模式，第二次之后不会进入onCreate周期，转而是onNewIntent
    // Non-standard startup mode
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent != null) {
            onIntentHandle(intent);
        }

        onInit();
        onListener();

    }

    @Override
    public abstract int layoutId();

    @Override
    public abstract void onInit();


    @Override
    public void onListener() {

    }


    @Override
    public void onBeforeCreate() {

    }

    @Override
    public void onPresenterCircle(T presenter) {

    }

    /**
     * 是否使用状态页
     */
    public boolean useStatusView() {
        return true;
    }

    public void showNetworkFail(String msg) {
        if (mMultipleStatusView != null) {
            if (this.getString(R.string.no_network_view_hint).equals(msg)) {
                mMultipleStatusView.showNoNetwork();
            } else {
                mMultipleStatusView.showError();
            }
        }
    }

    public void showContentEmpty() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showEmpty();
        }
    }

    public void showContentLoading() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showLoading();
        }
    }

    public void showContent() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showContent();
        }
    }

    public void showLoading() {
        dialog = new ProgressDialog(this);
        dialog.setTitle("loading data...");
        dialog.setCancelable(false);
        dialog.show();
    }

    public void hideLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 处理上个页面传递过来的值 <br />
     * Handle passed the previous page intent when first enter the page
     */
    @Override
    public void onIntentHandle(@NonNull Intent intent) {
    }

    @Override
    public void onBundleHandle(@NonNull Bundle savedInstanceState) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null && mUnBinder != Unbinder.EMPTY) mUnBinder.unbind();
        if (presenter != null) {
            presenter.end();
        }
        dialog = null;
    }
}
