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

import org.springframework.stereotype.Component;

import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntry;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntrySummary;
import edu.mayo.cts2.framework.model.directory.DirectoryResult;
import edu.mayo.cts2.framework.plugin.service.umls.index.ElasticSearchDao;
import edu.mayo.cts2.framework.plugin.service.umls.mapper.CodeSystemMapper;
import edu.mayo.cts2.framework.plugin.service.umls.mapper.RootSourceDTO;

import java.util.List;

/**
 * The Class CodeSystemRepository.
 *
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
@Component
public class CodeSystemRepository {
	
	@Resource
	private CodeSystemMapper codeSystemMapper;
	
	@Resource
	private CodeSystemFactory codeSystemFactory;
	
	@Resource
	private ElasticSearchDao elasticSearchDao;

	/**
	 * Gets the code system by sab.
	 *
	 * @param sab the sab
	 * @return the code system by sab
	 */
	public CodeSystemCatalogEntry getCodeSystemBySab(String sab){
		RootSourceDTO dto = this.codeSystemMapper.getRootSourceDTO(sab);
		
		if(dto != null){
			return codeSystemFactory.createCodeSystem(dto);
		} else {
			return null;
		}
	}
	
	
	public DirectoryResult<CodeSystemCatalogEntrySummary> searchCodeSystemDirectorySummaries(
            CodeSystemMapper.SearchObject searchObject, int start, int end) {
		
		List<RootSourceDTO> dto = this.codeSystemMapper.searchRootSourceDTOs(searchObject, start, end + 1);

        if(dto != null){
            List<CodeSystemCatalogEntrySummary> dtos = codeSystemFactory.createCodeSystem(dto);

            boolean atEnd = ! (dtos.size() == (end + 1) - start);

            if(! atEnd){
                dtos.remove(dto.size() - 1);
            }

            return new DirectoryResult<CodeSystemCatalogEntrySummary>(dtos, atEnd);
        } else {
            return null;
        }
		
	}
	
}
