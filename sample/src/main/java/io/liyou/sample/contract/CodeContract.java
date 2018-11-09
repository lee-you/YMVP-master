package io.liyou.sample.contract;

import android.app.Activity;

import java.io.File;

import io.liyou.sample.general.RequestListener;
import io.liyou.ymvp.contracts.YContract;

/**
 * Created by jiana on 16-11-21.
 */

public interface CodeContract {
    interface Presenter extends YContract.Presenter{
        void readCodeByFile(Activity activity, File file);
    }

    interface View extends YContract.View{
        void showLoadCode();
        void showCode(String code);
    }

    interface Model extends YContract.Model {

        void readCode(final Activity activity, final File file, final RequestListener<String> listener);
    }
}
