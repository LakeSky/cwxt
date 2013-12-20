package com.kzh.test;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class Program {
    public static void main(String[] args) throws Exception {
        MyTest test = new MyTest();
        test.scanEntitys();
    }
}
