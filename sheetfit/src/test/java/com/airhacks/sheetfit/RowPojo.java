package com.airhacks.sheetfit;

/*-
 * #%L
 * sheetfit
 * %%
 * Copyright (C) 2016 Adam Bien
 * %%
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
 * #L%
 */

/**
 *
 * @author airhacks.com
 */
public class RowPojo {

    private String first;
    private double second;
    private long third;

    public RowPojo(String first, double second, long third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public String getFirst() {
        return first;
    }

    public double getSecond() {
        return second;
    }

    public long getThird() {
        return third;
    }

}
