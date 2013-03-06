package edu.mayo.cts2.framework.plugin.service.umls.domain.mapentry;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import edu.mayo.cts2.framework.model.core.MapReference;
import edu.mayo.cts2.framework.model.core.MapVersionReference;
import edu.mayo.cts2.framework.model.core.NameAndMeaningReference;
import edu.mayo.cts2.framework.model.core.URIAndEntityName;
import edu.mayo.cts2.framework.model.mapversion.MapEntry;
import edu.mayo.cts2.framework.model.mapversion.MapSet;
import edu.mayo.cts2.framework.model.mapversion.MapTarget;
import edu.mayo.cts2.framework.model.mapversion.types.MapProcessingRule;
import edu.mayo.cts2.framework.plugin.service.umls.domain.entity.EntityUriHandler;
import edu.mayo.cts2.framework.plugin.service.umls.mapper.CuiToCodeDTO;

@Component
public class MapEntryFactory {
	
	@Resource
	private EntityUriHandler entityUriHandler;

	protected MapEntry createMapEntry(List<CuiToCodeDTO> dtos){
		MapEntry mapEntry = new MapEntry();
		mapEntry.setProcessingRule(MapProcessingRule.ALL_MATCHES);
		
		CuiToCodeDTO first = dtos.remove(0);

		URIAndEntityName sourceName = new URIAndEntityName();
		sourceName.setName(first.getConceptUI());
		sourceName.setNamespace("MTH");
		sourceName.setUri(
				this.entityUriHandler.getUri(
						"MTH", 
						first.getConceptUI()));
		
		mapEntry.setMapFrom(sourceName);
		
		MapSet mapSet = new MapSet();
		mapSet.setEntryOrder(1l);
		mapSet.setProcessingRule(MapProcessingRule.ALL_MATCHES);
		
		mapSet.addMapTarget(
				this.createMapTarget(
						first.getCodeUI(), 
						first.getRootSource(), 1));
		
		for(CuiToCodeDTO dto : dtos){
			mapSet.addMapTarget(
					this.createMapTarget(
							dto.getCodeUI(), 
							dto.getRootSource(),
							mapSet.getMapTargetCount() + 1));
		}
		
		mapEntry.addMapSet(mapSet);
		
		MapVersionReference ref = new MapVersionReference();
		ref.setMap(new MapReference("UMLS-Map"));
		ref.setMapVersion(new NameAndMeaningReference("2012AB"));
		mapEntry.setAssertedBy(ref);
		
		return mapEntry;
	}
	
	private MapTarget createMapTarget(String code, String sab, int index){
		MapTarget target = new MapTarget();
		target.setEntryOrder((long)index);
		
		URIAndEntityName name = new URIAndEntityName();
		name.setName(code);
		name.setNamespace(sab);
		name.setUri(
				this.entityUriHandler.getUri(
						sab, 
						code));
		
		target.setMapTo(name);
		
		return target;
	}
}
