package io.liyou.sample.view;

import android.support.design.widget.TextInputEditText;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import io.liyou.sample.R;
import io.liyou.sample.contract.LoginContract;
import io.liyou.sample.presenter.LoginPresenter;
import io.liyou.ymvp.view.base.YBaseActivity;

/**
 * Time: 2018/11/6 0006
 * Created by LiYou
 * Description :
 */
public class LoginActivity extends YBaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.et_account)
    EditText mEtAccount;
    @BindView(R.id.et_password)
    TextInputEditText mEtPassword;

    @Override
    public int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void loadStart() {
    }

    @Override
    public void loginEnd() {
        ArticleListActivity.launch(this);
//        HomeActivity.launch(this);
    }


    @OnClick(R.id.btn_login)
    public void onClickLogin() {
        presenter.login(mEtAccount.getText().toString(), mEtPassword.getText().toString());
    }


}
