package com.cl.privilege.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigUtil {
	
	private @Value("${cas.server.url}")String casServerUrl;
	private @Value("${cas.service.url}")String casServiceUrl;
	private @Value("${web.basepath}")String basePath;
	private @Value("${inc.basepath}")String incBasePath;
	
	public String getCasServerUrl() {
		return casServerUrl;
	}
	
	public String getCasServiceUrl() {
		return casServiceUrl;
	}

	public String getBasePath() {
		return basePath;
	}
	
	public String getIncBasePath() {
		return incBasePath;
	}
}
