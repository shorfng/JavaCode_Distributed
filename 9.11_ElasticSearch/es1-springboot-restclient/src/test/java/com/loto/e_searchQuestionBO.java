package com.loto;

import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
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
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class e_searchQuestionBO {
    @Autowired
    RestHighLevelClient client;

    public List<Map<String, Object>> searchQuestionBO(String scrollId) {
        // 搜索请求对象
        SearchRequest searchRequest = new SearchRequest("elasticsearch_test");

        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 设置搜索方法
        searchSourceBuilder.query(QueryBuilders.termQuery("name", "spring cloud实战"));
        searchSourceBuilder.fetchSource(new String[]{"name", "price", "timestamp"}, new String[]{});

        // 设置price 降序
        searchSourceBuilder.sort("price", SortOrder.DESC);

        // 请求对象设置 搜索源对象
        searchRequest.source(searchSourceBuilder);
        List<Map<String, Object>> questionBOList = new ArrayList<>();
        SearchResponse searchResponse = null;
        boolean deepSearch = false;
        try {
            deepSearch = true;
            searchRequest.source().size(2);
            final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(30L));
            searchRequest.scroll(scroll);
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            SearchHit[] searchHit = searchHits.getHits();
            long total = searchHits.getTotalHits().value;
            for (int i = 0; i < searchHit.length; i++) {
                SearchHit item = searchHit[i];
                questionBOList.add(item.getSourceAsMap());
            }
            return questionBOList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionBOList;
    }
}





