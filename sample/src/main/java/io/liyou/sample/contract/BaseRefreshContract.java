package io.liyou.sample.contract;

import java.util.List;

import io.liyou.ymvp.contracts.YContract;

/**
 * Time: 2018/11/7 0007
 * Created by LiYou
 * Description :
 */
public class BaseRefreshContract {
    public interface Presenter extends YContract.Presenter {
        /**
         * 请求数据
         */
        void requestLoadListData(int page);

        /**
         * 请求更新列表数据
         */
        void requestUpdateListData();
    }

    public interface View  extends YContract.View {

        /**
         * 更新列表成功
         */
        void updateListSuccess(List datas, boolean isEnd);

        /**
         * 更新失败
         */
        void updateListFail(String err);

        /**
         * 加载数据成功
         */
        void loadListDataSuccess(List datas, int currentPage, boolean isEnd);

        /**
         * 加载数据失败
         * @param err
         */
        void loadListDataFail(String err);

        /**
         * 数据已经被加载完
         */
        void loadListDateOver();

    }
}
