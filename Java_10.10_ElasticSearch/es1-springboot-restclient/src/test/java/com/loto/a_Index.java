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
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
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
public class a_Index {
    @Autowired
    RestHighLevelClient client;

    // 创建索引库
    /*
    PUT /elasticsearch_test
    {
      "settings": {},
      "mappings": {
        "properties": {
          "description": {
            "type": "text",
            "analyzer": "ik_max_word"
          },
          "name": {
            "type": "keyword"
          },
          "pic": {
            "type": "text",
            "index": false
          },
          "studymodel": {
            "type": "keyword"
          }
        }
      }
    }
    */
    @Test
    public void testCreateIndex() throws IOException {
        // 创建一个索引创建请求对象
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("elasticsearch_test");

        // 设置映射，方式1
        //XContentBuilder builder = XContentFactory.jsonBuilder()
        //        .startObject()
        //        .field("properties")
        //        .startObject()
        //        .field("description").startObject().field("type", "text").field("analyzer", "ik_max_word").endObject()
        //        .field("name").startObject().field("type", "keyword").endObject()
        //        .field("pic").startObject().field("type", "text").field("index", "false").endObject()
        //        .field("studymodel").startObject().field("type", "keyword").endObject()
        //        .endObject()
        //        .endObject();
        //createIndexRequest.mapping("doc", builder);

        // 设置映射，方式2
        createIndexRequest.mapping("doc", "{\n" +
                "        \"properties\": {\n" +
                "          \"description\": {\n" +
                "            \"type\": \"text\",\n" +
                "            \"analyzer\": \"ik_max_word\"\n" +
                "          },\n" +
                "          \"name\": {\n" +
                "            \"type\": \"keyword\"\n" +
                "          },\n" +
                "          \"pic\": {\n" +
                "            \"type\": \"text\",\n" +
                "            \"index\": false\n" +
                "          },\n" +
                "          \"studymodel\": {\n" +
                "            \"type\": \"keyword\"\n" +
                "          }\n" +
                "        }\n" +
                "      }", XContentType.JSON);

        // 操作索引的客户端
        IndicesClient indicesClient = client.indices();
        CreateIndexResponse createIndexResponse = indicesClient.create(createIndexRequest, RequestOptions.DEFAULT);

        // 得到响应
        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println(acknowledged);
    }

    @Test
    public void testDeleteIndex() throws IOException {
        // 构建 删除索引库的请求对象
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("elasticsearch_test");
        IndicesClient indicesClient = client.indices();

        AcknowledgedResponse deleteResponse = indicesClient.delete(deleteIndexRequest, RequestOptions.DEFAULT);

        // 得到响应
        boolean acknowledge = deleteResponse.isAcknowledged();
        System.out.println(acknowledge);
    }
}


