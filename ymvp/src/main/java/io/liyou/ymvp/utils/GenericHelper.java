/*
 * Copyright 2016 XuJiaji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.liyou.ymvp.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.liyou.ymvp.contracts.YContract;
import io.liyou.ymvp.presenters.YBasePresenter;

public class GenericHelper {

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getGenericClass(Class<?> klass, Class<?> filterClass) {
        Type type = klass.getGenericSuperclass();
        if (type == null || !(type instanceof ParameterizedType)) return null;
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        for (Type t : types) {
            Class<T> tClass = (Class<T>) t;
            if (filterClass.isAssignableFrom(tClass)) {
                return tClass;
            }
        }

        return null;
    }

    private static Class<?> getViewClass(Class<?> klass, Class<?> filterClass) {
        for (Class c : klass.getInterfaces()) {
            if (filterClass.isAssignableFrom(c)) return klass;
        }
        return getViewClass(klass.getSuperclass(), filterClass);
    }

    @SuppressWarnings("unchecked")
    public static <T> T newPresenter(Object obj) {
        if (!YContract.View.class.isInstance(obj)) {
            throw new RuntimeException("no implement YContract.BaseView");
        }
        try {
            Class<?> currentClass = obj.getClass();
            Class<?> viewClass = getViewClass(currentClass, YContract.View.class);
            Class<?> presenterClass = getGenericClass(viewClass, YContract.Presenter.class);
            Class<?> modelClass = getGenericClass(presenterClass, YContract.Model.class);
            YBasePresenter<?, ?> yBasePresenter = (YBasePresenter<?, ?>) presenterClass.newInstance();
            yBasePresenter.init(obj, modelClass.newInstance());
            return (T) yBasePresenter;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("instance of presenter fail\n" +
                " Remind presenter need to extends YBasePresenter");
    }

}