package com.loto;

import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
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
public class b_doc {
    @Autowired
    RestHighLevelClient client;

    //添加文档
    /*
    POST /elasticsearch_test/_doc/1
    {
      "name": "spring cloud实战",
      "description": "本课程主要从四个章节进行讲解： 1.微服务架构入门 2.spring cloud 基础入门 3.实战Spring Boot 4.注册中心eureka。",
      "studymodel":"201001",
      "timestamp": "2020-08-22 20:09:18",
      "price": 5.6
    }
     */
    @Test
    public void testAddDoc() throws IOException {
        // 准备索取请求对象
        //IndexRequest indexRequest  = new IndexRequest("elasticsearch_test","doc");
        IndexRequest indexRequest = new IndexRequest("elasticsearch_test");
        indexRequest.id("1");

        // 文档内容  准备json数据
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "spring cloud实战3");
        jsonMap.put("description", "本课程主要从四个章节进行讲解3： 1.微服务架构入门 2.spring cloud 基础入门 3.实战Spring Boot 4.注册中心eureka。");
        jsonMap.put("studymodel", "3101001");
        jsonMap.put("timestamp", "2020-07-22 20:09:18");
        jsonMap.put("price", 35.6);
        indexRequest.source(jsonMap);

        // 执行请求
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        DocWriteResponse.Result result = indexResponse.getResult();
        System.out.println(result);
    }

    // 查询文档
    @Test
    public void testGetDoc() throws IOException {
        // 查询请求对象
        GetRequest getRequest = new GetRequest("elasticsearch_test", "1");
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);

        // 得到文档内容
        Map<String, Object> sourceMap = getResponse.getSourceAsMap();
        System.out.println(sourceMap);
    }
}





