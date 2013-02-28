package edu.mayo.cts2.framework.plugin.service.umls.index;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import edu.mayo.cts2.framework.core.plugin.PluginConfigManager;
import fr.pilato.spring.elasticsearch.ElasticsearchAbstractClientFactoryBean;

public class ElasticSearchLocalClientFactory extends
		ElasticsearchAbstractClientFactoryBean {

	public static final String UMLS_CONFIG_NAMESPACE = "umls-service";
	
	protected Logger log = Logger.getLogger(this.getClass());

	@Autowired(required=false)
	private PluginConfigManager pluginConfigManager;
	
	private String indexDataDirectory;
	
	private Node node;

	@Override
	protected Client buildClient() throws Exception {
		ImmutableSettings.Builder settings = ImmutableSettings
				.settingsBuilder();
		settings.put("node.name", "nlm-node");
		settings.put("path.data", this.getDataDirectory());
		settings.put("http.enabled", false);

		log.info("Starting ElasticSearch Node.");
		this.node = nodeBuilder().
				settings(settings).
				local(true).
				data(true).
				node();

		Client client = this.node.client();

		return client;
	}
	
	protected String getDataDirectory(){
		if(StringUtils.isNotBlank(this.indexDataDirectory)){
			return indexDataDirectory;
		} else {
			Assert.notNull(this.pluginConfigManager);
			File workDirectory = 
				this.pluginConfigManager.getPluginWorkDirectory(UMLS_CONFIG_NAMESPACE);
			
			return workDirectory.getAbsolutePath();
		}
	}
	
	@Override
	public void destroy() throws Exception {
		log.info("Shutting down ElasticSearch Node.");
		super.destroy();
		this.node.stop();
	}

	public String getIndexDataDirectory() {
		return indexDataDirectory;
	}

	public void setIndexDataDirectory(String indexDataDirectory) {
		this.indexDataDirectory = indexDataDirectory;
	}
}
