package com.loto;

import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class c_query {
    @Autowired
    RestHighLevelClient client;

    //搜索全部记录
    /*
       GET   /elasticsearch_test/_search
        {
          "query":{
             "match_all":{}
          }
        }
    */
    @Test
    public void testSearchAll() throws IOException {
        // 搜索请求对象
        SearchRequest searchRequest = new SearchRequest("elasticsearch_test");
        searchRequest.searchType(SearchType.QUERY_THEN_FETCH);

        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 设置搜索方法
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.fetchSource(new String[]{"name", "price", "timestamp"}, new String[]{});

        // 请求对象设置 搜索源对象
        searchRequest.source(searchSourceBuilder);

        // 使用client  执行搜索
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 搜索结果
        SearchHits hits = searchResponse.getHits();

        // 匹配到的总记录数
        TotalHits totalHits = hits.getTotalHits();
        System.out.println("查询到的总记录数:" + totalHits.value);

        // 得到的匹配度高的文档
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String id = hit.getId();

            // 源文档的内容
            Map<String, Object> sourceMap = hit.getSourceAsMap();
            String name = (String) sourceMap.get("name");
            String timestamp = (String) sourceMap.get("timestamp");
            String description = (String) sourceMap.get("description");
            Double price = (Double) sourceMap.get("price");
            System.out.println(name);
            System.out.println(timestamp);
            System.out.println(description);
            System.out.println(price);
        }
    }

    @Test
    public void testTermQuery() throws IOException {
        // 搜索请求对象
        SearchRequest searchRequest = new SearchRequest("elasticsearch_test");

        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 设置搜索方法
        //searchSourceBuilder.query(QueryBuilders.termQuery("name","spring cloud实战"));
        searchSourceBuilder.query(QueryBuilders.termQuery("description", "spring"));
        searchSourceBuilder.fetchSource(new String[]{"name", "price", "timestamp"}, new String[]{});

        // 请求对象设置 搜索源对象
        searchRequest.source(searchSourceBuilder);

        // 使用client  执行搜索
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 搜索结果
        SearchHits hits = searchResponse.getHits();

        // 匹配到的总记录数
        TotalHits totalHits = hits.getTotalHits();
        System.out.println("查询到的总记录数:" + totalHits.value);

        // 得到的匹配度高的文档
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String id = hit.getId();
            // 源文档的内容
            Map<String, Object> sourceMap = hit.getSourceAsMap();
            String name = (String) sourceMap.get("name");
            String timestamp = (String) sourceMap.get("timestamp");
            String description = (String) sourceMap.get("description");
            Double price = (Double) sourceMap.get("price");
            System.out.println(name);
            System.out.println(timestamp);
            System.out.println(description);
            System.out.println(price);
        }
    }
}





