package io.liyou.sample.contract;


import io.liyou.sample.general.RequestListener;
import io.liyou.sample.model.entity.LoginEntity;
import io.liyou.ymvp.contracts.YContract;


public interface LoginContract {

    interface Presenter extends YContract.Presenter {

        void login(String account, String password);
    }

    interface View extends YContract.View {

        void loadStart();

        void loginEnd();
    }

    interface Model extends YContract.Model {

        void loginRequest(String userName, String password, final RequestListener<LoginEntity> listener);
    }
}
