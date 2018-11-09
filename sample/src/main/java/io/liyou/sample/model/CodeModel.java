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

import android.app.Activity;

import java.io.File;

import io.liyou.sample.contract.CodeContract;
import io.liyou.sample.general.RequestListener;
import io.liyou.sample.util.FileHelper;

/**
 * Created by jiana on 13/12/16.
 */
public class CodeModel implements CodeContract.Model {

    @Override
    public void readCode(final Activity activity, final File file, final RequestListener<String> listener) {
        new Thread() {
            @Override
            public void run() {
                FileHelper fileHelper = new FileHelper();
                fileHelper.readText(activity, file, listener);
            }
        }.start();
    }
}
