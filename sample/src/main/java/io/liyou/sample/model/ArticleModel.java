/*
 * Copyright 2016 XuJiaji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.liyou.sample.model;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import io.liyou.sample.api.Urls;
import io.liyou.sample.contract.ArticleDetailContract;
import io.liyou.sample.general.RequestListener;
import io.liyou.sample.http.callback.JsonCallback;
import io.liyou.sample.model.entity.ArticleEntity;
import io.liyou.sample.model.entity.BaseResponse;
import io.liyou.sample.model.entity.PageEntity;

/**
 * Created by liyou on 18/11/7.
 */
public class ArticleModel implements ArticleDetailContract.Model {


    @Override
    public void loadArticleList(int page, RequestListener<PageEntity<ArticleEntity>> listener) {
        OkGo.<BaseResponse<PageEntity<ArticleEntity>>>get(Urls.ARTICLE + page + "/json")
                .execute(new JsonCallback<BaseResponse<PageEntity<ArticleEntity>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<PageEntity<ArticleEntity>>> response) {
                        listener.requestSuccess(response.body().data);
                    }
                });
    }
}
