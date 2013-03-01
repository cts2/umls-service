package edu.mayo.cts2.framework.plugin.service.umls.profile.mapversion;

import java.util.Arrays;
import java.util.List;

import edu.mayo.cts2.framework.model.command.ResolvedReadContext;
import edu.mayo.cts2.framework.model.core.VersionTagReference;
import edu.mayo.cts2.framework.model.mapversion.MapVersion;
import edu.mayo.cts2.framework.model.service.core.NameOrURI;
import edu.mayo.cts2.framework.plugin.service.umls.profile.AbstractUmlsBaseService;
import edu.mayo.cts2.framework.service.profile.mapversion.MapVersionReadService;

public class UmlsMapVersionReadService extends AbstractUmlsBaseService
	implements MapVersionReadService {

	@Override
	public MapVersion readByTag(
			NameOrURI parentIdentifier,
			VersionTagReference tag, 
			ResolvedReadContext readContext) {
		// TODO This is incomplete
		MapVersion mapVersion = new MapVersion();
		
		String mapName = parentIdentifier.getName();
		String version = "2012AB";
		mapVersion.setMapVersionName(mapName + "-" + version);
		
		return mapVersion;
	}

	@Override
	public boolean existsByTag(NameOrURI parentIdentifier,
			VersionTagReference tag, ResolvedReadContext readContext) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<VersionTagReference> getSupportedTags() {
		return Arrays.asList(new VersionTagReference("CURRENT"));
	}

	@Override
	public MapVersion read(NameOrURI identifier, ResolvedReadContext readContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(NameOrURI identifier, ResolvedReadContext readContext) {
		// TODO Auto-generated method stub
		return false;
	}

}
