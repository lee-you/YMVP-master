package io.liyou.sample.presenter;

import android.widget.Toast;

import com.tencent.mmkv.MMKV;

import io.liyou.sample.App;
import io.liyou.sample.contract.LoginContract;
import io.liyou.sample.general.RequestListener;
import io.liyou.sample.model.LoginModel;
import io.liyou.sample.model.entity.LoginEntity;
import io.liyou.ymvp.presenters.YBasePresenter;


public class LoginPresenter extends YBasePresenter<LoginContract.View, LoginModel> implements LoginContract.Presenter {


    @Override
    public void login(String account, String password) {
        view.showLoading();
        model.loginRequest(account, password, new RequestListener<LoginEntity>() {
            @Override
            public void requestSuccess(LoginEntity loginEntity) {
                view.hideLoading();
                if(viewIsExist()){
                    MMKV.defaultMMKV().encode("uuid","haha");
                    view.loginEnd();
                }
            }

            @Override
            public void requestErr(String errStr) {
                view.hideLoading();
                Toast.makeText(App.getInstance(),errStr,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
