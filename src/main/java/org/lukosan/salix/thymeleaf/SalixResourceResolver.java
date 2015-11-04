package org.lukosan.salix.thymeleaf;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.lukosan.salix.SalixProperties;
import org.lukosan.salix.SalixScope;
import org.lukosan.salix.SalixService;
import org.lukosan.salix.SalixTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.util.Validate;

public class SalixResourceResolver implements IResourceResolver {
	
	private static final String NAME = "SALIX-RESOURCE";
	
	@Autowired
	private SalixProperties salixProperties;
	
	@Autowired
	private SalixService salixService;

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public InputStream getResourceAsStream(final TemplateProcessingParameters templateProcessingParameters, final String resourceName) {
		Validate.notNull(resourceName, "Resource name cannot be null");

		SalixTemplate template = null;
		
		if(salixProperties.isMultisite()) {
			if(WebContext.class.isAssignableFrom(templateProcessingParameters.getContext().getClass())) {
				String scope = ((WebContext) templateProcessingParameters.getContext()).getHttpServletRequest().getServerName();
				template = salixService.template(resourceName, scope);
				if(null == template)
					template = salixService.template(resourceName, SalixScope.SHARED);
			} else if(SalixContext.class.isAssignableFrom(templateProcessingParameters.getContext().getClass())) {
				String scope = ((SalixContext) templateProcessingParameters.getContext()).getScope();
				template = salixService.template(resourceName, scope);
			}
		} else
			template = salixService.template(resourceName);
			
		if(template != null)
			return IOUtils.toInputStream(template.getSource());
		
		return null;
	}

}
