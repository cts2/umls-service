package edu.mayo.cts2.framework.plugin.service.umls.profile;

import edu.mayo.cts2.framework.model.core.OpaqueData;
import edu.mayo.cts2.framework.model.core.SourceReference;
import edu.mayo.cts2.framework.model.service.core.DocumentedNamespaceReference;
import edu.mayo.cts2.framework.model.util.ModelUtils;
import edu.mayo.cts2.framework.service.profile.BaseService;

import java.util.List;

public class AbstractUmlsBaseService implements BaseService{
	private static final String MAYO = "Mayo Clinic";
	private static final String DEFAULT_VERSION = "1.0";
	private static final String DESCRIPTION = "UMLS -> CTS2 Service.";

	@Override
	public String getServiceVersion() {
		return DEFAULT_VERSION;
	}
	
	@Override
	public SourceReference getServiceProvider() {
		SourceReference ref = new SourceReference();
		ref.setContent(MAYO);
		
		return ref;
	}
	
	@Override
	public OpaqueData getServiceDescription() {
		return ModelUtils.createOpaqueData(DESCRIPTION);
	}

	@Override
	public String getServiceName() {
		return this.getClass().getCanonicalName();
	}

	@Override
	public List<DocumentedNamespaceReference> getKnownNamespaceList() {
		return null;
	}
}
