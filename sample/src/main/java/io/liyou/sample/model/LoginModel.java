package io.liyou.sample.model;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import io.liyou.sample.api.Urls;
import io.liyou.sample.contract.LoginContract;
import io.liyou.sample.general.RequestListener;
import io.liyou.sample.http.callback.JsonCallback;
import io.liyou.sample.model.entity.BaseResponse;
import io.liyou.sample.model.entity.LoginEntity;

/**
 * Time: 2018/11/6 0006
 * Created by LiYou
 * Description :
 */
public class LoginModel implements LoginContract.Model {

    @Override
    public void loginRequest(String userName, String password, final RequestListener<LoginEntity> listener) {
        OkGo.<BaseResponse<LoginEntity>>post(Urls.LOGIN)
                .params("username", userName)
                .params("password", password)
                .execute(new JsonCallback<BaseResponse<LoginEntity>>() {

                    @Override
                    public void onStart(Request<BaseResponse<LoginEntity>, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<BaseResponse<LoginEntity>> response) {
                        listener.requestSuccess(response.body().data);
                    }

                    @Override
                    public void onError(Response<BaseResponse<LoginEntity>> response) {
                        if (response.getException() != null) {
                            listener.requestErr(response.getException().getMessage());
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        listener.requestEnd();
                    }
                });
    }
}
