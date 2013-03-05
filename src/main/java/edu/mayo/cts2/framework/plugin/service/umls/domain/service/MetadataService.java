package edu.mayo.cts2.framework.plugin.service.umls.domain.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import edu.mayo.cts2.framework.plugin.service.umls.mapper.MetadataMapper;

@Component
public class MetadataService {
	
	@Resource
	private MetadataMapper metadataMapper;

}
