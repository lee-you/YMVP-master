package io.liyou.sample.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.liyou.sample.R;
import io.liyou.sample.contract.CodeContract;
import io.liyou.sample.presenter.CodePresenter;
import io.liyou.ymvp.view.base.v4.YBaseFragment;
import thereisnospon.codeview.CodeView;
import thereisnospon.codeview.CodeViewTheme;

/**
 * Created by jiana on 16-11-21.
 */

public class CodeFragment extends YBaseFragment<CodePresenter> implements CodeContract.View {
    public static final String KEY = "file";
    private ProgressDialog dialog;
    private File mFile;

    public static CodeFragment newInstance(File file) {
        CodeFragment cf = new CodeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, file);
        cf.setArguments(bundle);
        return cf;
    }

    @BindView(R.id.codeView)
    CodeView codeView;

    @Override
    public int layoutId() {
        return R.layout.fragment_code;
    }

    @Override
    public void onArgumentsHandle(@NonNull Bundle bundle) {
        mFile = (File) bundle.getSerializable(KEY);
    }

    @Override
    public void onInit() {
        ButterKnife.bind(this, getRootView());
        codeView.setTheme(CodeViewTheme.ANDROIDSTUDIO).fillColor();
    }

    @Override
    public void onLazyLoad() {
        presenter.readCodeByFile(getActivity(), mFile);
    }

    @Override
    public void showLoadCode() {
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("loading...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void showCode(String code) {
        codeView.showCode(code);
        codeView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 1000);
    }

    @Override
    public boolean isInViewPager() {
        return false;
    }
}
