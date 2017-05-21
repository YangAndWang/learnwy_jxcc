package com.learnwy.util;

/**
 * 用于返回额外的数据
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
