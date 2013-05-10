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
package edu.mayo.cts2.framework.plugin.service.umls.domain.codesystemversion;

import edu.mayo.cts2.framework.core.url.UrlConstructor;
import edu.mayo.cts2.framework.model.codesystemversion.CodeSystemVersionCatalogEntry;
import edu.mayo.cts2.framework.model.codesystemversion.CodeSystemVersionCatalogEntrySummary;
import edu.mayo.cts2.framework.model.core.*;
import edu.mayo.cts2.framework.model.util.ModelUtils;
import edu.mayo.cts2.framework.plugin.service.umls.domain.uri.CodeSystemVersionUriHandler;
import edu.mayo.cts2.framework.plugin.service.umls.domain.util.CodeSystemReferenceFactory;
import edu.mayo.cts2.framework.plugin.service.umls.mapper.SourceDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * A factory for creating CodeSystem objects.
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
@Component
public class CodeSystemVersionFactory {
	
	@Resource
	private CodeSystemVersionUriHandler codeSystemVersionUriHandler;

    @Resource
    private UrlConstructor urlConstructor;

    @Resource
    private CodeSystemReferenceFactory codeSystemReferenceFactory;

	private static final String LICENSE_CONTACT = "LicenseContact";

	private static final String CONTENT_CONTACT = "ContentContact";

    private static final SourceAndNotation UMLS_SOURCE_AND_NOTATION = new SourceAndNotation(){{
        this.setSourceAndNotationDescription("NLM UMLS");
    }};

	/**
	 * Creates a new CodeSystem object.
	 *
	 * @param sourceDTO the source dto
	 * @return the code system
	 */
	protected CodeSystemVersionCatalogEntry createCodeSystemVersion(SourceDTO sourceDTO){
        String sab = sourceDTO.getRootSource();
		String vsab = sourceDTO.getAbbreviation();
		
		CodeSystemVersionCatalogEntry entry = new CodeSystemVersionCatalogEntry();
		entry.setCodeSystemVersionName(vsab);

        String uri = this.codeSystemVersionUriHandler.getUri(vsab);
        entry.setAbout(uri);
        entry.setDocumentURI(uri);

        EntryDescription entryDescription = new EntryDescription();
        entryDescription.setValue(ModelUtils.toTsAnyType(sourceDTO.getExpandedForm()));
        entry.setResourceSynopsis(entryDescription);

        entry.setSourceAndNotation(UMLS_SOURCE_AND_NOTATION);

        entry.setVersionOf(this.codeSystemReferenceFactory.createReference(sab));

		return entry;
	}

    protected CodeSystemVersionCatalogEntrySummary createCodeSystemVersionSummary(SourceDTO sourceDTO){
        String sab = sourceDTO.getRootSource();
        String vsab = sourceDTO.getAbbreviation();

        CodeSystemVersionCatalogEntrySummary summary = new CodeSystemVersionCatalogEntrySummary();
        summary.setCodeSystemVersionName(vsab);

        String uri = this.codeSystemVersionUriHandler.getUri(vsab);
        summary.setAbout(uri);
        summary.setDocumentURI(uri);
        summary.setHref(this.urlConstructor.createCodeSystemUrl(vsab));

        EntryDescription entryDescription = new EntryDescription();
        entryDescription.setValue(ModelUtils.toTsAnyType(sourceDTO.getExpandedForm()));
        summary.setResourceSynopsis(entryDescription);

        summary.setVersionOf(this.codeSystemReferenceFactory.createReference(sab));

        return summary;
    }
	
	private SourceAndRoleReference createSourceAndRole(String role, String value){
		SourceAndRoleReference ref = new SourceAndRoleReference();
		ref.setSource(new SourceReference(value));
		ref.setRole(new RoleReference(role));
		
		return ref;
	}

    protected List<CodeSystemVersionCatalogEntrySummary> createCodeSystemVersion(List<SourceDTO> dtos) {
        List<CodeSystemVersionCatalogEntrySummary> returnList = new ArrayList<CodeSystemVersionCatalogEntrySummary>();

        for(SourceDTO dto : dtos){
            returnList.add(this.createCodeSystemVersionSummary(dto));
        }

        return returnList;
    }
}
