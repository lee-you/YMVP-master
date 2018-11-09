package io.liyou.sample.http.callback;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.liyou.sample.model.entity.BaseResponse;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Time: 2018/11/6 0006
 * Created by LiYou
 * Description :
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
//        request.headers("header1", "HeaderValue1")//
//                .params("params1", "ParamsValue1")//
//                .params("token", "3215sdf13ad1f65asd4f3ads1f");
    }

    @SuppressWarnings("unchecked")
    @Override
    public T convertResponse(Response response)throws Throwable {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        //这里得到第二层泛型的所有类型
        Type type = params[0];

        if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
        //这里得到第二层数据的真实类型
        Type rawType = ((ParameterizedType) type).getRawType();
        //这里得到第二层数据的真实类型的泛型
        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];

        ResponseBody body = response.body();
        if (body == null) return null;
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(body.charStream());
        if (rawType != BaseResponse.class) {
            T data = gson.fromJson(jsonReader, type);
            response.close();
            return data;
        } else {
            BaseResponse baseResponse = gson.fromJson(jsonReader, type);
            response.close();

            int code = baseResponse.errorCode;
            if (code == 0) {
                return (T) baseResponse;
            } else {
                throw new IllegalStateException(baseResponse.errorMsg);
            }

        }
    }
}
