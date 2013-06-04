package edu.mayo.cts2.framework.plugin.service.umls.domain.entity;

import edu.mayo.cts2.framework.model.core.*;
import edu.mayo.cts2.framework.model.entity.*;
import edu.mayo.cts2.framework.model.entity.types.DesignationRole;
import edu.mayo.cts2.framework.model.util.ModelUtils;
import edu.mayo.cts2.framework.plugin.service.umls.domain.uri.CodeSystemUriHandler;
import edu.mayo.cts2.framework.plugin.service.umls.index.IndexableEntity.Description;
import edu.mayo.cts2.framework.plugin.service.umls.index.IndexedEntity;
import edu.mayo.cts2.framework.plugin.service.umls.mapper.CodeDTO;
import edu.mayo.cts2.framework.plugin.service.umls.mapper.ConceptDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class EntityFactory {
	
	public static final String MTH_NAMESPACE = "MTH";
	
	private static final URIAndEntityName CONCEPT_TYPE = new URIAndEntityName();
	static {
		CONCEPT_TYPE.setName("Concept");
		CONCEPT_TYPE.setNamespace("skos");
		CONCEPT_TYPE.setUri("http://www.w3.org/2004/02/skos/core#Concept");
	}
	
	@Resource
	private EntityUriHandler entityUriHandler;
	
	@Resource
	private CodeSystemUriHandler codeSystemUriHandler;
	
	public EntityDescription createEntity(ConceptDTO conceptDto){
		NamedEntityDescription namedEntity = new NamedEntityDescription();
		
		String sab = MTH_NAMESPACE;
		String name = conceptDto.getUi();
		namedEntity.setEntityID(ModelUtils.createScopedEntityName(name, sab));
		
		namedEntity.setAbout(entityUriHandler.getUri(sab, name));
		
		Designation designation = new Designation();
		designation.setValue(ModelUtils.toTsAnyType(conceptDto.getString()));
		
		namedEntity.addDesignation(designation);
		namedEntity.setDescribingCodeSystemVersion(
			this.buildCodeSystemVersionReference(sab, null));
		
		namedEntity.addEntityType(CONCEPT_TYPE);

		EntityDescription entity = new EntityDescription();

		entity.setNamedEntity(namedEntity);
		
		return entity;
	}
	
	public EntityDescription createEntity(List<CodeDTO> codeDtos){
		
		if (codeDtos == null)
			return null;
		
		if (codeDtos.isEmpty())
			return null;
		
		NamedEntityDescription namedEntity = new NamedEntityDescription();
		
		String sab = null;
		String name = null;
		
		for (CodeDTO codeDto : codeDtos)
		{
			if ((sab == null)||(name == null))
			{
					sab = codeDto.getSab();
					name = codeDto.getUi();
					namedEntity.setEntityID(ModelUtils.createScopedEntityName(name, sab));
		
					namedEntity.setAbout(entityUriHandler.getUri(sab, name));
					namedEntity.addEntityType(CONCEPT_TYPE);
					namedEntity.setDescribingCodeSystemVersion(
							this.buildCodeSystemVersionReference(sab, null));

			}
		
			Designation designation = new Designation();
			designation.setValue(ModelUtils.toTsAnyType(codeDto.getName()));
			LanguageReference lref = new LanguageReference(codeDto.getLanguage());
			designation.setLanguage(lref);
			
			if ("yes".equalsIgnoreCase(codeDto.isPreferred()))
				designation.setDesignationRole(DesignationRole.PREFERRED);
			
			namedEntity.addDesignation(designation);
		}		

		EntityDescription entity = new EntityDescription();
		entity.setNamedEntity(namedEntity);
		
		return entity;
	}
	
	public EntityReference createEntityReference(List<CodeDTO> codeDtos){
		
		if (codeDtos == null)
			return null;
		
		if (codeDtos.isEmpty())
			return null;
		
		String sab = null;
		String name = null;

		EntityReference entityR = new EntityReference();

		for (CodeDTO codeDto : codeDtos)
		{
			if ((sab == null)||(name == null))
			{
					sab = codeDto.getSab();
					name = codeDto.getUi();

					DescriptionInCodeSystem descInCs = new DescriptionInCodeSystem();
					descInCs.setHref(entityUriHandler.getUri(sab, name));
					CodeSystemVersionReference csvRef = buildCodeSystemVersionReference(sab, null);
					descInCs.setDescribingCodeSystemVersion(csvRef);
					descInCs.setDesignation(codeDto.getName());
					entityR.addKnownEntityDescription(descInCs);
					entityR.setAbout(entityUriHandler.getUri(sab, name));
			}
		}		

		return entityR;
	}
	
	public EntityDirectoryEntry createEntityDirectoryEntry(IndexedEntity indexedEntity){
		EntityDirectoryEntry entry = new EntityDirectoryEntry();
		
		String sab = indexedEntity.getSab();
		String name = indexedEntity.getName();
		String vsab = indexedEntity.getVsab();
		
		entry.setName(ModelUtils.createScopedEntityName(name, sab));
		
		entry.setAbout(entityUriHandler.getUri(sab, name));

		//TODO: Need to better determine ranking
		Description description = indexedEntity.getDescriptions().get(0);
		
		DescriptionInCodeSystem descriptionInCodeSystem = new DescriptionInCodeSystem();
		descriptionInCodeSystem.setDesignation(description.getValue());
		descriptionInCodeSystem.
			setDescribingCodeSystemVersion(
				this.buildCodeSystemVersionReference(sab, vsab));
		
		entry.addKnownEntityDescription(descriptionInCodeSystem);
		
		entry.setMatchStrength(indexedEntity.getScore());
		
		return entry;
	}
	
	public EntityListEntry createEntityListEntry(IndexedEntity indexedEntity){
		EntityListEntry entry = new EntityListEntry();
		
		String sab = indexedEntity.getSab();
		String name = indexedEntity.getName();
		String vsab = indexedEntity.getVsab();
		
		EntityDescription ed = new EntityDescription();

		//TODO: Need to better determine ranking
		Description description = indexedEntity.getDescriptions().get(0);
		
		DescriptionInCodeSystem descriptionInCodeSystem = new DescriptionInCodeSystem();
		descriptionInCodeSystem.setDesignation(description.getValue());
		descriptionInCodeSystem.
			setDescribingCodeSystemVersion(
				this.buildCodeSystemVersionReference(sab, vsab));

		
		
		entry.setHref(entityUriHandler.getUri(sab, name));
		entry.setEntry(ed);
		entry.setMatchStrength(indexedEntity.getScore());
		
		return entry;
	}

	public EntityDescription createEntityDescription(IndexedEntity indexedEntity){
		
		String sab = indexedEntity.getSab();
		String name = indexedEntity.getName();
		String vsab = indexedEntity.getVsab();
		
		EntityDescription ed = new EntityDescription();

		NamedEntityDescription namedEntity = new NamedEntityDescription();
		
		if ((sab != null)&&(name != null))
		{
				namedEntity.setEntityID(ModelUtils.createScopedEntityName(name, sab));
	
				namedEntity.setAbout(entityUriHandler.getUri(sab, name));
				namedEntity.addEntityType(CONCEPT_TYPE);
				namedEntity.setDescribingCodeSystemVersion(
						this.buildCodeSystemVersionReference(sab, vsab));
				
				//namedEntity.setDescribingCodeSystemVersion(describingCodeSystemVersion)
		}

		ed.setNamedEntity(namedEntity);
		return ed;
	}

	public EntityListEntry createEntityListEntry(EntityDescription ed){

		if (ed == null)
			return null;

		EntityListEntry entry = new EntityListEntry();
		String name = ed.getNamedEntity().getEntityID().getName();
		String sab = ed.getNamedEntity().getEntityID().getNamespace();
		entry.setHref(entityUriHandler.getUri(sab, name));
		entry.setEntry(ed);
		
		return entry;
	}
	
	private CodeSystemVersionReference buildCodeSystemVersionReference(String sab, String vsab){
		
		if ((sab == null)&&(vsab == null))
			return null;
		
		String vsabTemp = vsab;
		if (vsabTemp == null)
			vsabTemp = sab;
		
		NameAndMeaningReference versionRef = new NameAndMeaningReference();
		versionRef.setContent(vsabTemp);
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
