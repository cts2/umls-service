package edu.mayo.cts2.framework.plugin.service.umls.domain.entity;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import edu.mayo.cts2.framework.model.core.CodeSystemReference;
import edu.mayo.cts2.framework.model.core.CodeSystemVersionReference;
import edu.mayo.cts2.framework.model.core.DescriptionInCodeSystem;
import edu.mayo.cts2.framework.model.core.NameAndMeaningReference;
import edu.mayo.cts2.framework.model.entity.EntityDirectoryEntry;
import edu.mayo.cts2.framework.model.util.ModelUtils;
import edu.mayo.cts2.framework.plugin.service.umls.domain.codesystem.CodeSystemUriHandler;
import edu.mayo.cts2.framework.plugin.service.umls.index.IndexableEntity.Description;
import edu.mayo.cts2.framework.plugin.service.umls.index.IndexedEntity;

@Component
public class EntityFactory {
	
	@Resource
	private EntityUriHandler entityUriHandler;
	
	@Resource
	private CodeSystemUriHandler codeSystemUriHandler;
	
	public EntityDirectoryEntry createEntityDirectoryEntry(IndexedEntity indexedEntity){
		EntityDirectoryEntry entry = new EntityDirectoryEntry();
		
		String sab = indexedEntity.getSab();
		String name = indexedEntity.getName();
		entry.setName(ModelUtils.createScopedEntityName(name, sab));
		
		entry.setAbout(entityUriHandler.getUri(sab, name));

		//TODO: Need to better determine ranking
		Description description = indexedEntity.getDescriptions().get(0);
		
		DescriptionInCodeSystem descriptionInCodeSystem = new DescriptionInCodeSystem();
		descriptionInCodeSystem.setDesignation(description.getValue());
		descriptionInCodeSystem.
			setDescribingCodeSystemVersion(
				this.buildCodeSystemVersionReference(sab));
		
		entry.addKnownEntityDescription(descriptionInCodeSystem);
		
		entry.setMatchStrength(indexedEntity.getScore());
		
		return entry;
	}
	
	private CodeSystemVersionReference buildCodeSystemVersionReference(String sab){
		NameAndMeaningReference versionRef = new NameAndMeaningReference();
		versionRef.setContent(sab);
		versionRef.setUri(this.codeSystemUriHandler.getUri(sab));

		CodeSystemReference codeSystemRef = new CodeSystemReference();
		codeSystemRef.setContent(sab);
		codeSystemRef.setUri(this.codeSystemUriHandler.getUri(sab));
		
		CodeSystemVersionReference ref = new CodeSystemVersionReference();
		ref.setCodeSystem(codeSystemRef);
		ref.setVersion(versionRef);

		return ref;
	}

}
