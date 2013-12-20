package com.kzh.test;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTest {
    public void scanEntitys() throws Exception {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        String classpath = this.getClass().getResource("/").toString();
        Map<String, String> entityClass = new HashMap<String, String>();
        Resource[] resources = resourcePatternResolver.getResources("classpath*:/com/kzh/*/*/entity/*.class");
        for (int i = 0; i < resources.length; i++) {
            String source = resources[i].getURL().toString();
            String str = source.substring(classpath.length(), source.length() - 6).replace("/", ".");
            String className = str.substring(str.lastIndexOf(".") + 1);
            entityClass.put(className, str);
        }
        System.out.println(entityClass);
    }
}
