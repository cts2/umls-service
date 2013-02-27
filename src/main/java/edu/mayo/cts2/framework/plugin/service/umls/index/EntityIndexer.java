package edu.mayo.cts2.framework.plugin.service.umls.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import edu.mayo.cts2.framework.plugin.service.umls.index.IndexableEntity.Description;
import edu.mayo.cts2.framework.plugin.service.umls.mapper.AtomRowDTO;

@Component
public class EntityIndexer implements InitializingBean {
	
	private static final int CACHE_SIZE = 10000;

	protected Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private SqlSession sqlSession;
	
	@Resource
	private ElasticSearchDao elasticSearchDao;
	
	@Override
	public void afterPropertiesSet() throws Exception {

		if(! this.elasticSearchDao.doesIndexExist()){
			log.warn("No index detected... building.");
			
			new Runnable(){
				@Override
				public void run() {
					indexEntities();
				}
			}.run();
		}
	}
	
	private static class FlushMarker {
		private boolean flushReady = false;
		private String lastKey = null;
	}

	public void indexEntities(){
		log.info("Starting Entity indexing...");
		
		final List<AtomRowDTO> cache = new ArrayList<AtomRowDTO>();
		
		final FlushMarker flushMarker = new FlushMarker();

		this.sqlSession.select("edu.mayo.cts2.framework.plugin.service.umls.mapper.CodeSystemMapper.streamEntities", new ResultHandler(){

			@Override
			public void handleResult(ResultContext context) {
				AtomRowDTO row = (AtomRowDTO)context.getResultObject();
			
				if(cache.size() == CACHE_SIZE){
					flushMarker.flushReady = true;
					flushMarker.lastKey = getUniqueKey(row);
				}
				
				if(flushMarker.flushReady){
					String newKey = getUniqueKey(row);
					
					if(! newKey.equals(flushMarker.lastKey)){
						flushCache(cache);
						flushMarker.flushReady = false;
						flushMarker.lastKey = null;
					}
				}
				
				cache.add(row);
			}
		});
		
		flushCache(cache);
		
		this.elasticSearchDao.refresh();
		
		log.info("Entity indexing complete.");
	}
	
	protected void flushCache(List<AtomRowDTO> cache){
		log.info("Flushing cache.");
		Map<String,IndexableEntity> groups = new HashMap<String,IndexableEntity>();

		for(AtomRowDTO row : cache){
			String currentKey = this.getUniqueKey(row);
			if(! groups.containsKey(currentKey)){
				IndexableEntity entity = new IndexableEntity();
				entity.setName(row.getUi());
				entity.setSab(row.getRootSource());
				entity.getDescriptions().add(new Description(row.getString()));
				
				groups.put(currentKey, entity);
			} else {
				groups.get(currentKey).getDescriptions().add(new Description(row.getString()));
			}
		}
		
		this.elasticSearchDao.index(groups.values());
		cache.clear();
	}
	
	private String getUniqueKey(AtomRowDTO row){
		return row.getUi() + row.getRootSource();
	}

}
