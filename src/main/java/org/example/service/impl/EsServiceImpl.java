package org.example.service.impl;

import com.google.gson.Gson;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.example.domin.EsGoods;
import org.example.service.EsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EsServiceImpl implements EsService {

    private static final String ARTICLE_INDEX = "goods_recommendation";

    @Resource
    private RestHighLevelClient client;

    @Override
    public boolean createIndexOfArticle() {
        Settings settings = Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 1)
                .build();
// {"properties":{"author":{"type":"text"},
// "content":{"type":"text","analyzer":"ik_max_word","search_analyzer":"ik_smart"}
// ,"title":{"type":"text","analyzer":"ik_max_word","search_analyzer":"ik_smart"},
// ,"createDate":{"type":"date","format":"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd"}
// }
        String mapping = "{\"properties\":{\"user_id\":{\"type\":\"text\"},\n" +
                "\"goods_id\":{\"type\":\"text\",\"analyzer\":\"ik_max_word\",\"search_analyzer\":\"ik_smart\"}\n" +
                "},\"url\":{\"type\":\"text\"}\n" +
                "}";
        CreateIndexRequest indexRequest = new CreateIndexRequest(ARTICLE_INDEX)
                .settings(settings).mapping(mapping,XContentType.JSON);
        CreateIndexResponse response = null;
        try {
            response = client.indices().create(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response!=null) {
            System.err.println(response.isAcknowledged() ? "success" : "default");
            return response.isAcknowledged();
        } else {
            return false;
        }
    }

    //测试索引是否存在
    @Override
    public boolean testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("goods_recommendation");
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }

    @Override
    public IndexResponse addEsGoods(EsGoods esGoods) {
        Gson gson = new Gson();
        String s = gson.toJson(esGoods);
        //创建索引创建对象
        IndexRequest indexRequest = new IndexRequest(ARTICLE_INDEX);
        //文档内容
        indexRequest.source(s,XContentType.JSON);
        //通过client进行http的请求
        IndexResponse re = null;
        try {
            re = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return re;
    }

    @Override
    public List<EsGoods> queryByKey(String keyword) {
        SearchRequest request = new SearchRequest();
        /*
         * 创建  搜索内容参数设置对象:SearchSourceBuilder
         * 相对于matchQuery，multiMatchQuery针对的是多个field，也就是说，当multiMatchQuery中，fieldNames参数只有一个时，其作用与matchQuery相当；
         * 而当fieldNames有多个参数时，如field1和field2，那查询的结果中，要么field1中包含text，要么field2中包含text。
         */
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders
                .multiMatchQuery(keyword, "user_id"));
        request.source(searchSourceBuilder);
        List<EsGoods> result = new ArrayList<>();
        try {
            SearchResponse search = client.search(request, RequestOptions.DEFAULT);
            for (SearchHit hit:search.getHits()){
                Map<String, Object> map = hit.getSourceAsMap();
                EsGoods item = new EsGoods();
                item.setUserId((String) map.get("user_id"));
                item.setGoodsId((String) map.get("goods_id"));
                result.add(item);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public EsGoods queryById(String indexId) {
        GetRequest request = new GetRequest(ARTICLE_INDEX, "_doc", indexId);
        GetResponse response = null;
        try {
            response = client.get(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response.isExists()){
            Gson gson = new Gson();
            return gson.fromJson(response.getSourceAsString(),EsGoods.class);
        }
        return null;
    }


}
