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
package edu.mayo.cts2.framework.plugin.service.umls.profile.codesystemversion;

import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntry;
import edu.mayo.cts2.framework.model.codesystemversion.CodeSystemVersionCatalogEntry;
import edu.mayo.cts2.framework.model.command.ResolvedReadContext;
import edu.mayo.cts2.framework.model.core.VersionTagReference;
import edu.mayo.cts2.framework.model.service.core.NameOrURI;
import edu.mayo.cts2.framework.plugin.service.umls.domain.codesystem.CodeSystemRepository;
import edu.mayo.cts2.framework.plugin.service.umls.domain.codesystemversion.CodeSystemVersionRepository;
import edu.mayo.cts2.framework.plugin.service.umls.profile.AbstractUmlsBaseService;
import edu.mayo.cts2.framework.service.profile.codesystem.CodeSystemReadService;
import edu.mayo.cts2.framework.service.profile.codesystemversion.CodeSystemVersionReadService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
public class UmlsCodeSystemVersionReadService extends AbstractUmlsBaseService implements CodeSystemVersionReadService {

	@Resource
	private CodeSystemVersionRepository codeSystemVersionRepository;

    @Transactional
	@Override
	public CodeSystemVersionCatalogEntry read(
			NameOrURI identifier,
			ResolvedReadContext readContext) {
		if(identifier.getName() != null){
			return this.codeSystemVersionRepository.getCodeSystemByVsab(identifier.getName());
		} else {
			throw new UnsupportedOperationException("Read by URI not supported.");
		}
	}

    @Transactional
	@Override
	public boolean exists(NameOrURI identifier, ResolvedReadContext readContext) {

		return (this.read(identifier, readContext) != null);
	}

    @Transactional
    @Override
    public boolean existsVersionId(NameOrURI codeSystem, String officialResourceVersionId) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    @Override
    public CodeSystemVersionCatalogEntry getCodeSystemByVersionId(NameOrURI codeSystem, String officialResourceVersionId, ResolvedReadContext readContext) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    @Override
    public CodeSystemVersionCatalogEntry readByTag(NameOrURI parentIdentifier, VersionTagReference tag, ResolvedReadContext readContext) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    @Override
    public boolean existsByTag(NameOrURI parentIdentifier, VersionTagReference tag, ResolvedReadContext readContext) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<VersionTagReference> getSupportedTags() {
        throw new UnsupportedOperationException();
    }
}
