package com.ead.notificationhex.adapters.configs;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class ResolverConfig extends WebMvcConfigurationSupport {

	private PageableHandlerMethodArgumentResolver resolver;
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
		
		resolver = new PageableHandlerMethodArgumentResolver();
		
		argumentResolvers.add(resolver);
		
		super.addArgumentResolvers(argumentResolvers);
	}
}
