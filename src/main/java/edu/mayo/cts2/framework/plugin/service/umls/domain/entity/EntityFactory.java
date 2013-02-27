package edu.mayo.cts2.framework.plugin.service.umls.domain.entity;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import edu.mayo.cts2.framework.model.core.DescriptionInCodeSystem;
import edu.mayo.cts2.framework.model.entity.EntityDirectoryEntry;
import edu.mayo.cts2.framework.model.util.ModelUtils;
import edu.mayo.cts2.framework.plugin.service.umls.index.IndexableEntity;
import edu.mayo.cts2.framework.plugin.service.umls.index.IndexableEntity.Description;

@Component
public class EntityFactory {
	
	@Resource
	private EntityUriHandler entityUriHandler;
	
	public EntityDirectoryEntry createEntityDirectoryEntry(IndexableEntity indexedEntity){
		EntityDirectoryEntry entry = new EntityDirectoryEntry();
		
		String sab = indexedEntity.getSab();
		String name = indexedEntity.getName();
		entry.setName(ModelUtils.createScopedEntityName(name, sab));
		
		entry.setAbout(entityUriHandler.getUri(sab, name));

		for(Description description : indexedEntity.getDescriptions()){
			DescriptionInCodeSystem descriptionInCodeSystem = new DescriptionInCodeSystem();
			descriptionInCodeSystem.setDesignation(description.getValue());
			
			entry.addKnownEntityDescription(descriptionInCodeSystem);
		}
		
		return entry;
	}

}
