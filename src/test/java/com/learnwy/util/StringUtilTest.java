package com.learnwy.util;

import org.junit.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {
    @Test
    public void empty() throws Exception {
        Assert.assertTrue(StringUtil.empty(""));
        Assert.assertFalse(StringUtil.empty(null));
        Assert.assertFalse(StringUtil.empty(" "));
    }

    @Test
    public void join() throws Exception {

    }

    @Test
    public void trimPath() throws Exception {
    }

    @Test
    public void isNullOrEmpty() throws Exception {
    }

    @Test
    public void trimPathLangCN() throws Exception {
    }

    @Test
    public void parseToLong() throws Exception {
    }

    @Test
    public void canParseLong() throws Exception {
    }

}