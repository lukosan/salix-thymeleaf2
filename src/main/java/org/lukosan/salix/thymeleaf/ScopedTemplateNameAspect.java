package org.lukosan.salix.thymeleaf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.thymeleaf.Template;
import org.thymeleaf.TemplateProcessingParameters;

@Aspect
public class ScopedTemplateNameAspect {

	private static final Log logger = LogFactory.getLog(ScopedTemplateNameAspect.class);
	
    @Around("execution(* org.thymeleaf.TemplateRepository.getTemplate(..)) && args(templateProcessingParameters)")
	public Object template(ProceedingJoinPoint pjp, TemplateProcessingParameters templateProcessingParameters) throws Throwable {
		
		logger.debug("Calling getTemplate...");
		
		Template template = (Template) pjp.proceed(new Object[] {templateProcessingParameters});
        
		logger.debug("Returning from getTemplate...");
		
        return template;
    }
	
}
