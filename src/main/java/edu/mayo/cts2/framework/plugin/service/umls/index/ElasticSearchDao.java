package edu.mayo.cts2.framework.plugin.service.umls.index;

import javax.annotation.Resource;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ElasticSearchDao {
	
	private static final String INDEX_NAME = "umls";
	private static final String ENTITY_TYPE = "entity";
	
	private ObjectMapper jsonMapper = new ObjectMapper();
	
	@Resource
	private Client client;
	
	public void index(Iterable<IndexableEntity> indexedEntities) {
		BulkRequestBuilder bulkRequest = client.prepareBulk();

		for(IndexableEntity entity : indexedEntities){
			bulkRequest.add(this.doGetIndexRequest(entity));
		}
		
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
		    throw new RuntimeException(bulkResponse.buildFailureMessage());
		}
	}
	
	public boolean doesIndexExist(){
		return this.client.prepareCount(INDEX_NAME).execute().actionGet().getCount() > 0;
	}
	
	protected IndexRequest doGetIndexRequest(IndexableEntity indexedEntity){
		String json;
		try {
			json = this.jsonMapper.writeValueAsString(indexedEntity);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		return client.prepareIndex(INDEX_NAME, ENTITY_TYPE).setSource(json).request();
	}
	
	public void refresh(){
		this.client.admin().indices().prepareRefresh().execute().actionGet();
	}
	
	public SearchHits search(QueryBuilder queryBuilder, int start, int end){
		return this.client.
			prepareSearch(INDEX_NAME).
			setTypes("entity").
			setQuery(queryBuilder).
			execute().
			actionGet().
			getHits();
	}

	public long count(QueryBuilder queryBuilder){
		SearchResponse sh = this.client.
			prepareSearch(INDEX_NAME).
			setTypes("entity").
			setQuery(queryBuilder).
			execute().
			actionGet();
		return sh.getHits().totalHits();
	}
}
