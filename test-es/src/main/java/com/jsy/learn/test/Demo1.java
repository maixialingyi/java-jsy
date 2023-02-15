package com.jsy.learn.test;

import com.jsy.learn.ESClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

public class Demo1 {

    @Test
    public void testGetESClient(){
        RestHighLevelClient client = ESClient.getClient();
        System.out.println("ok");
    }
}
