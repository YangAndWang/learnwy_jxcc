package com.learnwy.util;

/**
 * Created by 25973 on 2017-05-07.
 */
public class TranValueClass {
    Object value;

    public TranValueClass() {

    }

    public TranValueClass(Object value) {
        this.value = value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
