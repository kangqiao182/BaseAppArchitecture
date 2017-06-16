/*
 * Copyright 2016 jeasonlzy(廖子尧)
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
package com.jzsec.broker.okgo;

import java.io.Serializable;


/**
 * Created by zhaopan on 2017/6/14.
 * e-mail: kangqiao610@gmail.com
 */

public class DataResponse<T> extends BaseResponse<T> {

    public int code;
    public String msg;
    public String data;

    @Override
    public String toString() {
        return "DataResponse{\n" +//
                "\tcode=" + code + "\n" +//
                "\tmsg='" + msg + "\'\n" +//
                "\tdata=" + data + "\n" +//
                '}';
    }

    @Override
    public String getDataString() {
        return data;
    }

}