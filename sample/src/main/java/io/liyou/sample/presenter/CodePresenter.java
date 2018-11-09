package io.liyou.sample.presenter;

import android.app.Activity;

import java.io.File;

import io.liyou.sample.contract.CodeContract;
import io.liyou.sample.model.CodeModel;
import io.liyou.ymvp.presenters.YBasePresenter;

/**
 * Created by jiana on 16-11-21.
 */

public class CodePresenter extends YBasePresenter<CodeContract.View, CodeModel> implements CodeContract.Presenter {

    @Override
    public void readCodeByFile(Activity activity, File file) {
//        view.showLoadCode();
//        model.readCode(activity, file, new RequestListenerProxy<String>(this)
//        {
//            @Override
//            public void success(String s)
//            {
//                view.showCode(s);
//            }
//        });
    }
}
