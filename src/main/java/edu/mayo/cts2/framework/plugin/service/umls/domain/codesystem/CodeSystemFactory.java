/*
* Copyright: (c) 2004-2013 Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Except as contained in the copyright notice above, or as used to identify
* MFMER as the author of this software, the trade names, trademarks, service
* marks, or product names of the copyright holder shall not be used in
* advertising, promotion or otherwise in connection with this software without
* prior written authorization of the copyright holder.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package edu.mayo.cts2.framework.plugin.service.umls.domain.codesystem;

import javax.annotation.Resource;

import edu.mayo.cts2.framework.core.url.UrlConstructor;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntrySummary;
import edu.mayo.cts2.framework.model.core.EntryDescription;
import edu.mayo.cts2.framework.model.util.ModelUtils;
import edu.mayo.cts2.framework.plugin.service.umls.domain.uri.CodeSystemUriHandler;
import org.springframework.stereotype.Component;

import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntry;
import edu.mayo.cts2.framework.model.core.RoleReference;
import edu.mayo.cts2.framework.model.core.SourceAndRoleReference;
import edu.mayo.cts2.framework.model.core.SourceReference;
import edu.mayo.cts2.framework.plugin.service.umls.mapper.RootSourceDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory for creating CodeSystem objects.
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
@Component
public class CodeSystemFactory {
	
	@Resource
	private CodeSystemUriHandler codeSystemUriHandler;

    @Resource
    private UrlConstructor urlConstructor;

	private static final String LICENSE_CONTACT = "LicenseContact";

	private static final String CONTENT_CONTACT = "ContentContact";

	/**
	 * Creates a new CodeSystem object.
	 *
	 * @param rootSourceDTO the root source dto
	 * @return the code system
	 */
	protected CodeSystemCatalogEntry createCodeSystem(RootSourceDTO rootSourceDTO){
		String sab = rootSourceDTO.getAbbreviation();
		
		CodeSystemCatalogEntry entry = new CodeSystemCatalogEntry();
		entry.setCodeSystemName(sab);
		entry.setAbout(this.codeSystemUriHandler.getUri(sab));
		entry.setFormalName(rootSourceDTO.getShortName());
		entry.addSourceAndRole(this.createSourceAndRole(CONTENT_CONTACT, rootSourceDTO.getContentContact()));
		entry.addSourceAndRole(this.createSourceAndRole(LICENSE_CONTACT, rootSourceDTO.getLicenseContact()));

        EntryDescription entryDescription = new EntryDescription();
        entryDescription.setValue(ModelUtils.toTsAnyType(rootSourceDTO.getExpandedForm()));
        entry.setResourceSynopsis(entryDescription);

        entry.setVersions(this.urlConstructor.createVersionsOfCodeSystemUrl(sab));
		
		return entry;
	}

    protected CodeSystemCatalogEntrySummary createCodeSystemSummary(RootSourceDTO rootSourceDTO){
        String sab = rootSourceDTO.getAbbreviation();

        CodeSystemCatalogEntrySummary summary = new CodeSystemCatalogEntrySummary();
        summary.setCodeSystemName(sab);
        summary.setAbout(this.codeSystemUriHandler.getUri(sab));
        summary.setFormalName(rootSourceDTO.getShortName());
        summary.setHref(this.urlConstructor.createCodeSystemUrl(sab));
        summary.setVersions(this.urlConstructor.createVersionsOfCodeSystemUrl(sab));

        EntryDescription entryDescription = new EntryDescription();
        entryDescription.setValue(ModelUtils.toTsAnyType(rootSourceDTO.getExpandedForm()));
        summary.setResourceSynopsis(entryDescription);

        return summary;
    }
	
	private SourceAndRoleReference createSourceAndRole(String role, String value){
		SourceAndRoleReference ref = new SourceAndRoleReference();
		ref.setSource(new SourceReference(value));
		ref.setRole(new RoleReference(role));
		
		return ref;
	}

    protected List<CodeSystemCatalogEntrySummary> createCodeSystem(List<RootSourceDTO> dtos) {
        List<CodeSystemCatalogEntrySummary> returnList = new ArrayList<CodeSystemCatalogEntrySummary>();

        for(RootSourceDTO dto : dtos){
            returnList.add(this.createCodeSystemSummary(dto));
        }

        return returnList;
    }
}
