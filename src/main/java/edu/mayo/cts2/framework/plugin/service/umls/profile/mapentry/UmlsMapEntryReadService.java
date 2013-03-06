package edu.mayo.cts2.framework.plugin.service.umls.profile.mapentry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import edu.mayo.cts2.framework.model.command.ResolvedReadContext;
import edu.mayo.cts2.framework.model.mapversion.MapEntry;
import edu.mayo.cts2.framework.plugin.service.umls.domain.mapentry.MapEntryRepository;
import edu.mayo.cts2.framework.plugin.service.umls.profile.AbstractUmlsBaseService;
import edu.mayo.cts2.framework.service.profile.mapentry.MapEntryReadService;
import edu.mayo.cts2.framework.service.profile.mapentry.name.MapEntryReadId;

@Component
public class UmlsMapEntryReadService extends AbstractUmlsBaseService 
	implements MapEntryReadService  {

	@Resource
	private MapEntryRepository mapEntryRepository;
	
	@Override
	public MapEntry read(
			MapEntryReadId identifier,
			ResolvedReadContext readContext) {
		
		String cui = identifier.getEntityName().getName();
		
		//this should be in the form:
		//MTH-SNOMEDCT-2012AB
		//for example
		String mapVersionName = 
			identifier.getMapVersion().getName();
		
		String[] parts = StringUtils.split(mapVersionName, '-');
		
		return this.mapEntryRepository.getMapEntryFromSourceCui(cui, parts[1]);	
	}

	@Override
	public boolean exists(MapEntryReadId identifier,
			ResolvedReadContext readContext) {
		// TODO Auto-generated method stub
		return false;
	}

}