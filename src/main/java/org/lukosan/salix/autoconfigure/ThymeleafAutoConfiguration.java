package org.lukosan.salix.autoconfigure;

import javax.annotation.PostConstruct;
import javax.servlet.Servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lukosan.salix.SalixHandlerMapping;
import org.lukosan.salix.SalixScopeRegistry;
import org.lukosan.salix.thymeleaf.SalixDialect;
import org.lukosan.salix.thymeleaf.SalixProcessor;
import org.lukosan.salix.thymeleaf.SalixResourceResolver;
import org.lukosan.salix.thymeleaf.ScopedTemplateNameAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
@ConditionalOnClass(SpringTemplateEngine.class)
@AutoConfigureBefore(org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration.class)
@EnableConfigurationProperties(ThymeleafProperties.class)
@ConditionalOnProperty(prefix = "salix.thymeleaf", name = "enabled", matchIfMissing = true)
public class ThymeleafAutoConfiguration {

	@Configuration
	public static class SalixDefaultTemplateResolverConfiguration {
		
		private static final Log logger = LogFactory.getLog(SalixDefaultTemplateResolverConfiguration.class);
				
		@Autowired
		private ThymeleafProperties properties;
		@Autowired
		private SalixScopeRegistry registry;

		@Bean(name="salixResourceResolver")
		public IResourceResolver salixResourceResolver() {
			SalixResourceResolver resolver = new SalixResourceResolver();
			return resolver;
		}
		
		@Bean(name="salixTemplateResolver")
		public ITemplateResolver salixTemplateResolver() {
			TemplateResolver resolver = new TemplateResolver();
			resolver.setResourceResolver(salixResourceResolver());
			resolver.setOrder(1);
			resolver.setPrefix("");
			resolver.setSuffix("");
			resolver.setTemplateMode(this.properties.getMode());
			resolver.setCharacterEncoding(this.properties.getEncoding());
			resolver.setCacheable(false); // cache only uses template name for uniqueness
			return resolver;
		}
		
		@Bean
		public ScopedTemplateNameAspect aspect() {
			return new ScopedTemplateNameAspect();
		}
		
		@Bean
		public SalixDialect salixDialect() {
			return new SalixDialect(new SalixProcessor());
		}
		
		@PostConstruct
		public void postConstruct() {
			if(logger.isInfoEnabled())
				logger.info("PostConstruct " + getClass().getSimpleName());
		}
		
	}
	
	@Configuration
	@ConditionalOnClass({ Servlet.class })
	@ConditionalOnWebApplication
	@ConditionalOnProperty(prefix = "salix.thymeleaf.frontend", name = "enabled", matchIfMissing = true)
	public static class ThymeleafViewResolverConfiguration {

		@Bean
		public SalixHandlerMapping salixHandlerMapping() {
			return new SalixHandlerMapping();
		}

	}

}
