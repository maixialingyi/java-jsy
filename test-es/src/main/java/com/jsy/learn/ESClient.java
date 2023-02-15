package com.jsy.learn;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class ESClient {

    public static RestHighLevelClient getClient(){
        HttpHost httpHost = new HttpHost("127.0.0.1",9200);
        RestClientBuilder clientBuilder = RestClient.builder(httpHost);
        RestHighLevelClient client = new RestHighLevelClient(clientBuilder);
        return client;
    }

}
