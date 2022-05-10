package org.example.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright(c)lbhbinhao@163.com
 * @author liubinhao
 * @date 2021/3/3
 */
@Configuration
public class EsConfig {

    @Value("${elasticsearch.schema}")
    private String schema;
    @Value("${elasticsearch.address}")
    private String address;
    @Value("${elasticsearch.port}")
    private Integer port;

    @Bean
    public RestHighLevelClient restHighLevelClient() {

        RestClientBuilder builder = RestClient.builder(new HttpHost(address, port, schema));
        return new RestHighLevelClient(builder);
    }

}
