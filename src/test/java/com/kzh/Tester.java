package com.kzh;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: kzh
 * Date: 13-4-25
 * Time: 上午9:16
 */
public class Tester {
    @Test
    public void display() {
        String a = "?";
        int b = a.indexOf("?");
        System.out.println(b);
    }

    @Test
    public void showResource() throws Exception {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        System.out.println(this.getClass().getResource("/"));
//        this.getClass().getResource("/");

        Resource[] resources = resourcePatternResolver.getResources("classpath*:/com/kzh/*/*/entity/*.class");
        for (int i = 0; i < resources.length; i++) {
            FileSystemResource fs = (FileSystemResource) resources[i];
            System.out.println(fs.getClass());
            System.out.println(fs.getURI());
            System.out.println(fs.getURL());
            System.out.println(fs.getPath());
            System.out.println();
        }

        System.out.println(resources.getClass());
    }

    @Test
    public void testJson() {
        Map map = new HashMap();
        map.put("key", "value");
        map.put("xiaoming", "wangjiang");

        Map mapChildren = new HashMap();
        mapChildren.put("key", "value");
        mapChildren.put("xiaoming", "wangjiang");
        map.put("children", JSONArray.fromObject(mapChildren));
        List list = new ArrayList();
        list.add(map);
        JSONArray jsonArray = JSONArray.fromObject(list);
//        jsonObject.put("result", list);
//        System.out.println(jsonArray.toString(""));
    }
}
